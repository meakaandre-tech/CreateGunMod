package com.nukateam.cgs.common.faundation.entity;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.nukateam.cgs.common.ntgl.CgsAmmoHolders;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.nukateam.ntgl.common.data.WeaponData;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.nukateam.ntgl.common.util.util.math.ExtendedEntityRayTraceResult;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import com.geckolib.animatable.manager.AnimatableManager;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import java.util.function.Predicate;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.block.BaseFireBlock;
import static com.geckolib.util.GeckoLibUtil.createInstanceCache;

public class BlazeProjectile extends ProjectileEntity implements ItemSupplier, AnimatedProjectile {
    protected final AnimatableInstanceCache cache = createInstanceCache(this);

    private boolean isSuperHeated;
    private boolean isStrong;

    public BlazeProjectile(EntityType<? extends ProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }
    
    public BlazeProjectile(EntityType<? extends ProjectileEntity> entityType, Level level, WeaponData data) {
        super(entityType, level, data);
        isSuperHeated = WeaponStateHelper.getCurrentAmmo(data) == CgsAmmoHolders.BLAZE_CAKE;
        isStrong = !WeaponStateHelper.hasAttachmentEquipped(weapon, CgsAttachmentTypes.ENGINE);
    }

    protected Predicate<BlockState> getBlockFilter() {
        return (value) -> false;
    }

    @Override
    public void tick() {
        if(!isStrong && isInWater()) {
            this.remove(RemovalReason.KILLED);
        }
        super.tick();
    }

    @Override
    public @NotNull ItemStack getItem() {
        if(isStrong)
            return new ItemStack(Items.FIRE_CHARGE);
        else return ItemStack.EMPTY;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isSuperHeated", this.isSuperHeated);
        compound.putBoolean("isStrong", this.isStrong);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput compound) {
        super.readAdditionalSaveData(compound);
        this.isSuperHeated = compound.getBooleanOr("isSuperHeated", false);
        this.isStrong = compound.getBooleanOr("isStrong", false);
    }

    @Override
    protected void onProjectileTick() {
        if (this.level().isClientSide()) {

            if(isSuperHeated){
                for (int i = 5; i > 0; i--) {
                    this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, false, this.getX() - (this.getDeltaMovement().x() / i), this.getY() - (this.getDeltaMovement().y() / i), this.getZ() - (this.getDeltaMovement().z() / i), 0, 0, 0);
                }
            }
            else {
                for (int i = 5; i > 0; i--) {
                    this.level().addParticle(ParticleTypes.FLAME, true, false, this.getX() - (this.getDeltaMovement().x() / i), this.getY() - (this.getDeltaMovement().y() / i), this.getZ() - (this.getDeltaMovement().z() / i), 0, 0, 0);
                }
            }

            if (this.level().random.nextInt(2) == 0) {
                this.level().addParticle(ParticleTypes.SMOKE, true, false, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
//                this.level().addParticle(ParticleTypes.FLAME, true, false, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitEntity(ExtendedEntityRayTraceResult result) {
        super.onHitEntity(result);

        if (this.level() instanceof ServerLevel serverlevel) {
            var target = result.getEntity();
            var owner = this.getOwner();
            var fireTicks = target.getRemainingFireTicks();
            target.igniteForSeconds(5.0F);
            var damageSource = this.damageSources().fireball(null, owner);

            if (!target.hurtServer(serverlevel, damageSource, 5.0F)) {
                target.setRemainingFireTicks(fireTicks);
            } else {
                EnchantmentHelper.doPostAttackEffects(serverlevel, target, damageSource);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult, BlockState state) {
        super.onHitBlock(blockHitResult, state);

        var blockpos = blockHitResult.getBlockPos();
        var face = blockHitResult.getDirection();

        if (!this.level().isClientSide()) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || (this.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.MOB_GRIEFING))) {
                blockpos = blockpos.relative(face);
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
