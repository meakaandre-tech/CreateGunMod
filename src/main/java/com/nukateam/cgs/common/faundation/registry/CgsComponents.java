package com.nukateam.cgs.common.faundation.registry;

import com.mojang.serialization.Codec;
import com.nukateam.ntgl.Ntgl;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import com.nukateam.ntgl.platform.DeferredHolder;
import com.nukateam.ntgl.platform.DeferredRegister;

import javax.annotation.Nullable;

public class CgsComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTER =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Ntgl.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COCK =
            REGISTER.register("cock", () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT)
                    .build());


    public static void register() {
    }
}