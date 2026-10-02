package com.nukateam.cgs.client.animators;

import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animatable.manager.AnimatableManager;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import net.minecraft.world.item.ItemDisplayContext;

public class NailgunAnimator extends EngineAnimator {
    public NailgunAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer renderer) {
        super(transformType, renderer);
    }
}
