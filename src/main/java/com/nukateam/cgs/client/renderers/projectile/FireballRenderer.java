package com.nukateam.cgs.client.renderers.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nukateam.ntgl.client.render.renderers.projectiles.ProjectileRenderer;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;

public class FireballRenderer extends ProjectileRenderer {
    public FireballRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ProjectileEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState cameraState, int light) {
        if (entity.tickCount >= 2 || !(cameraState.pos.distanceToSqr(entity.position()) < 12.25D)) {
            poseStack.pushPose();
            poseStack.rotate(cameraState.orientation);
            poseStack.rotate(Axis.YP.rotationDegrees(180.0F));
            renderItem(entity.getItem(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, collector, entity);
            poseStack.popPose();
        }
    }
}
