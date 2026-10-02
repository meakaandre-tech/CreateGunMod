package com.nukateam.cgs.client.animators;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.geo.render.DynamicGeoItemRenderer;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import net.minecraft.world.item.ItemDisplayContext;

import static com.geckolib.animation.object.LoopType.LOOP;
import static com.geckolib.animation.RawAnimation.begin;

public class BlazegunAnimator extends WeaponAnimator {
    public static final String SHOT_AUTO = "shot_auto";
    private boolean isAuto;

    public BlazegunAnimator(ItemDisplayContext transformType, DynamicGeoItemRenderer renderer) {
        super(transformType, renderer);
    }
    @Override
    protected void tick() {
        super.tick();
        try {
            if (itemStack.getItem() instanceof WeaponItem) {
                this.isAuto = WeaponStateHelper.hasAttachmentEquipped(getStack(), CgsAttachmentTypes.ENGINE);
            }
        } catch (IllegalStateException e) {
            Gunsmithing.LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected RawAnimation getShootingAnimation(AnimationTest<WeaponAnimator> event) {
        if(isAuto){
            var animation = begin().then(getGunAnim(SHOT_AUTO), LOOP);
            animationHelper.syncAnimation(event, rate, SHOT_AUTO);
            return animation;
        }
        return super.getShootingAnimation(event);
    }
}
