package com.nukateam.cgs;

import com.mojang.logging.LogUtils;
import com.nukateam.cgs.common.faundation.registry.*;
import com.nukateam.cgs.common.faundation.registry.items.*;
import com.nukateam.cgs.common.handlers.GuanoAccumulationHandler;
import com.nukateam.cgs.common.handlers.GunEventHandler;
import com.nukateam.cgs.common.handlers.MeleeHandler;
import com.nukateam.cgs.common.network.PacketHandler;
import com.nukateam.cgs.common.ntgl.CgsAmmoHolders;
import com.nukateam.cgs.common.ntgl.CgsAmmoType;
import com.nukateam.cgs.common.ntgl.CgsProjectileRegistry;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.platform.PlatformHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class Gunsmithing implements ModInitializer {
    public static final String MOD_ID = "cgs";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        CgsWeapons.register();
        CgsItems.register();
        CgsAmmo.register();
        CgsAttachments.register();
        CgsBlocks.register();
        CgsItemTabs.register();
        CgsSounds.register();
        CgsProjectiles.register();
        CgsParticles.register();
        CgsAmmoType.register();
        CgsAmmoHolders.register();
        CgsComponents.register();

        PacketHandler.register();
        CgsProjectileRegistry.registerProjectiles();

        Ntgl.EVENT_BUS.register(GunEventHandler.class);
        Ntgl.EVENT_BUS.register(MeleeHandler.class);
        Ntgl.EVENT_BUS.register(GuanoAccumulationHandler.class);

        // the lava tank burns like a lava bucket
        FuelValueEvents.BUILD.register((builder, context) ->
                builder.add(CgsItems.LAVA_CONTAINER.get(), 20000));

        // ore generation (was data/cgs/neoforge/biome_modifier)
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE, cgsResource("lead_ore_placed")));
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE, cgsResource("sulfur_ore_placed")));
    }

    public static @NotNull Identifier cgsResource(String name) {
        return Identifier.fromNamespaceAndPath(Gunsmithing.MOD_ID, name);
    }

    public static boolean isDebugging() {
        return PlatformHelper.isDevelopment();
    }
}
