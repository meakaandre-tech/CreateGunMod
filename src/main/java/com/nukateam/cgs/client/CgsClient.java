package com.nukateam.cgs.client;

import com.nukateam.cgs.client.animators.*;
import com.nukateam.cgs.client.handlers.InputEventHandler;
import com.nukateam.cgs.client.renderers.projectile.CgsProjectileRenderer;
import com.nukateam.cgs.client.renderers.projectile.FireballRenderer;
import com.nukateam.cgs.client.renderers.weapon.BaseWeaponRenderer;
import com.nukateam.cgs.client.renderers.weapon.FlintlockRenderer;
import com.nukateam.cgs.client.renderers.weapon.GatlingRenderer;
import com.nukateam.cgs.client.renderers.weapon.HammerRenderer;
import com.nukateam.cgs.common.faundation.registry.CgsParticles;
import com.nukateam.cgs.common.faundation.registry.CgsProjectiles;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.client.registry.WeaponRegistry;
import com.nukateam.ntgl.client.render.renderers.projectiles.ProjectileRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.FlameParticle;

public class CgsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(CgsProjectiles.FIREBALL.get(), FireballRenderer::new);
        EntityRendererRegistry.register(CgsProjectiles.ROCKET.get(), CgsProjectileRenderer::new);
        EntityRendererRegistry.register(CgsProjectiles.SMALL_ROCKET.get(), CgsProjectileRenderer::new);
        EntityRendererRegistry.register(CgsProjectiles.NAIL.get(), CgsProjectileRenderer::new);
        EntityRendererRegistry.register(CgsProjectiles.SPEAR.get(), CgsProjectileRenderer::new);
        EntityRendererRegistry.register(CgsProjectiles.INCENDIARY.get(), ProjectileRenderer::new);

        ParticleProviderRegistry.getInstance().register(CgsParticles.BLUE_FLAME.get(), FlameParticle.Provider::new);

        WeaponRegistry.registerRenderer(CgsWeapons.FLINTLOCK.get(), new FlintlockRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.REVOLVER .get(), new BaseWeaponRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.SHOTGUN  .get(), new BaseWeaponRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.NAILGUN  .get(), new BaseWeaponRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.GATLING  .get(), new GatlingRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.BLAZEGUN .get(), new BaseWeaponRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.LAUNCHER .get(), new BaseWeaponRenderer());
        WeaponRegistry.registerRenderer(CgsWeapons.HAMMER   .get(), new HammerRenderer());

        WeaponRegistry.registerAnimator(CgsWeapons.FLINTLOCK.get(), FlintlockAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.REVOLVER.get(), RevolverAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.SHOTGUN .get(), ShotgunAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.NAILGUN .get(), NailgunAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.GATLING .get(), GatlingAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.BLAZEGUN.get(), BlazegunAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.LAUNCHER.get(), LauncherAnimator::new);
        WeaponRegistry.registerAnimator(CgsWeapons.HAMMER  .get(), HammerAnimator::new);

        Ntgl.EVENT_BUS.register(InputEventHandler.class);
    }
}
