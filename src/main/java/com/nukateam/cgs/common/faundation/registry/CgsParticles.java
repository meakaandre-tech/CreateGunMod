package com.nukateam.cgs.common.faundation.registry;

import com.nukateam.cgs.Gunsmithing;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

import com.nukateam.ntgl.platform.DeferredHolder;
import com.nukateam.ntgl.platform.DeferredRegister;
import com.nukateam.ntgl.platform.DeferredRegister;

public class CgsParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, Gunsmithing.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLUE_FLAME =
            PARTICLE_TYPES.register("blue_flame", () -> new SimpleParticleType(false));


    public static void register() {
    }
}
