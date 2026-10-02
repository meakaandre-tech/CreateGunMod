package com.nukateam.cgs.client.model;

import com.geckolib.animatable.GeoAnimatable;
import com.nukateam.geo.render.AnimatableGeoModel;
import com.nukateam.ntgl.client.model.IGlowingModel;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class ProjectileModel<T extends Entity & GeoAnimatable> extends AnimatableGeoModel<T> implements IGlowingModel<T> {
    @Override
    public Identifier getModelResource(T animator) {
        return getResource(animator, "projectile/", "");
    }

    @Override
    public Identifier getAnimationResource(T animator) {
        return getResource(animator, "projectile/", "");
    }

    @Override
    public Identifier getTextureResource(T animator) {
        var name = getName(animator);
        return getResource(animator, "textures/projectile/" + name + "/", ".png");
    }

    @Override
    public Identifier getGlowingTextureResource(T animator) {
        var name = getName(animator);
        return getResource(animator, "textures/projectile/" + name + "/", "_glowmask.png");
    }

    @Override
    public RenderType getRenderType(T animatable, Identifier texture) {
        return RenderTypes.entityTranslucent(getTextureResource(animatable));
    }

    public Identifier getResource(T animator, String path, String extension) {
        var id = BuiltInRegistries.ENTITY_TYPE.getKey(animator.getType());
        var modId = id.getNamespace();
        var name = id.getPath();
        return Identifier.tryBuild(modId, path + name + extension);
    }

    private String getName(T animator) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(animator.getType()).getPath();
    }
}
