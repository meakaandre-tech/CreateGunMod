package com.nukateam.cgs.common.utils;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class BreakHandler {
    private final BiPredicate<BlockState, ToolMaterial> isToolTierSufficient;
    private final Predicate<BlockState> isMineable;
    private final Predicate<BlockState> isCanDrop;

    public BreakHandler(BiPredicate<BlockState, ToolMaterial> isToolTierSufficient, Predicate<BlockState> isMineable, Predicate<BlockState> isCanDrop){
        this.isToolTierSufficient = isToolTierSufficient;
        this.isMineable = isMineable;
        this.isCanDrop = isCanDrop;
    }

    public boolean isToolTierSufficient(BlockState blockState, ToolMaterial toolTier) {
        return isToolTierSufficient.test(blockState, toolTier);

    }

    public boolean isMineable(BlockState blockState) {
        return isMineable.test(blockState);
    }

    public boolean isCanDrop(BlockState blockState) {
        return isCanDrop.test(blockState);
    }
}
