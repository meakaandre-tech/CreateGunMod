package com.nukateam.cgs.common.faundation.item;

import com.nukateam.cgs.common.faundation.registry.items.CgsItems;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

/**
 * A stackable fluid tank that works like a bucket. The burn time of the lava tank and the crafting
 * remainder are set where the items are registered (fuel registry / item properties).
 */
public class FluidContainerItem extends BucketItem {
    private final Fluid fluidSupplier;

    public FluidContainerItem(Fluid fluid, Properties properties) {
        super(fluid, properties);
        this.fluidSupplier = fluid;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        var blockpos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var relative = blockpos.relative(direction);

        if (level.mayInteract(player, blockpos) && player.mayUseItemAt(relative, direction, stack)) {
            var pos = hitResult.getBlockPos();
            var state = level.getBlockState(pos);
            var fluid = state.getFluidState().getType();

            if (this.fluidSupplier == Fluids.EMPTY) {
                var pickuresult = tryPickupFluid(player, level, hitResult, fluid);

                if (pickuresult.consumesAction()) {
                    var container = handleContainerAfterUse(fluid, player, stack, true);
                    return success(level).heldItemTransformedTo(container);
                }
            } else {
                var placementResult = tryPlaceFluid(player, level, hitResult, stack);

                if (placementResult.consumesAction()) {
                    var container = handleContainerAfterUse(fluid, player, stack, false);
                    return success(level).heldItemTransformedTo(container);
                }
            }
        }

        return InteractionResult.PASS;
    }

    private static InteractionResult.Success success(Level level) {
        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }

    private InteractionResult tryPickupFluid(Player player, Level level, BlockHitResult hitResult, Fluid stack) {
        var pos = hitResult.getBlockPos();
        var state = level.getBlockState(pos);

        if (state.getBlock() instanceof BucketPickup bucketPickup) {
            bucketPickup.pickupBlock(player, level, pos, state);
            player.awardStat(Stats.ITEM_USED.get(this));
            bucketPickup.getPickupSound().ifPresent((soundEvent) -> {
                player.playSound(soundEvent, 1.0F, 1.0F);
            });
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private InteractionResult tryPlaceFluid(Player player, Level level, BlockHitResult hitResult, ItemStack stack) {
        var blockpos = hitResult.getBlockPos();
        var direction = hitResult.getDirection();
        var relativePos = blockpos.relative(direction);
        var blockstate = level.getBlockState(blockpos);
        var placePos = canBlockContainFluid(player, level, blockpos, blockstate) ? blockpos : relativePos;

        if (level.mayInteract(player, blockpos) && player.mayUseItemAt(relativePos, direction, stack)) {
            if (this.emptyContents(player, level, placePos, hitResult)) {
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, placePos, stack);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResult.SUCCESS;
            }
            else return InteractionResult.FAIL;
        }
        return InteractionResult.FAIL;
    }

    private ItemStack handleContainerAfterUse(Fluid targetFluid, Player player, ItemStack stack, boolean filled) {
        if(!player.isCreative()) {
            var newContainer = filled ? getFilledContainerForFluid(targetFluid)
                    : CgsItems.EMPTY_CONTAINER.get();

            if (newContainer == null) return stack;

            var newStack = new ItemStack(newContainer);
            newStack.applyComponents(stack.getComponentsPatch());
            stack.shrink(1);

            if (stack.isEmpty()) {
                return newStack;
            } else {
                if (!player.getInventory().add(newStack)) {
                    player.drop(newStack, false);
                }
                return stack;
            }
        }
        return stack;
    }

    private Item getFilledContainerForFluid(Fluid fluid) {
        for (var entry : CgsItems.CONTAINERS.entrySet()) {
            if (entry.getKey() == fluid) {
                return entry.getValue().get();
            }
        }
        return null;
    }

    protected boolean canBlockContainFluid(Player player, Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock())
                .canPlaceLiquid(player, worldIn, posIn, blockstate, this.fluidSupplier);
    }
}
