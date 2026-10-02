package com.nukateam.cgs.client.renderers.weapon;

import com.geckolib.animation.state.BoneSnapshots;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.model.gun.GeoWeaponModel;
import com.nukateam.ntgl.client.render.renderers.weapon.DynamicWeaponRenderer;
import com.nukateam.ntgl.client.util.ClientDebug;
import com.nukateam.ntgl.client.util.helpers.TransformUtils;
import com.zurrtum.create.AllItems;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BaseWeaponRenderer extends DynamicWeaponRenderer<WeaponAnimator> {
    public BaseWeaponRenderer() {
        super(new GeoWeaponModel());
    }

    public BaseWeaponRenderer(GeoModel model) {
        super(model);
    }

    private ItemDisplayContext transformType;

    @Override
    public void render(LivingEntity entity, ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                       SubmitNodeCollector collector, int packedLight) {
        this.transformType = transformType;

        poseStack.pushPose();
        poseStack.translate(ClientDebug.X / 10d / 16D, ClientDebug.Y / 10d / 16D, ClientDebug.Z / 10d / 16D);

        var hasExtendoGrip = entity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP;

        if(hasExtendoGrip && transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            poseStack.translate(1 / 16D, -3 / 16D, -3 / 16D);
        }

        super.render(entity, stack, transformType, poseStack, collector, packedLight);
        poseStack.popPose();
    }

    @Override
    protected void updateBone(RenderPassInfo<GeoRenderState> renderPassInfo, GeoBone bone, BoneSnapshots snapshots) {
        super.updateBone(renderPassInfo, bone, snapshots);

        if(!TransformUtils.isFirstPerson(transformType)
                && (bone.name().equals("muzzle_effect") || bone.name().equals("muzzle_flash"))) {
            setHidden(snapshots, bone, true);
        }
    }

    @Override
    protected void renderArms(PoseStack poseStack, GeoBone bone, int packedLight, int packedOverlay, SubmitNodeCollector collector) {
        var hasExtendoGrip = currentEntity.getOffhandItem().getItem() == AllItems.EXTENDO_GRIP;

        if (!hasExtendoGrip) {
            super.renderArms(poseStack, bone, packedLight, packedOverlay, collector);
        }
    }
}
