package com.nukateam.cgs.client.renderers.projectile;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.nukateam.cgs.client.model.ProjectileModel;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class CgsProjectileRenderer<T extends ProjectileEntity & GeoAnimatable, R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<T, R> {
    public static final int MAX_SIZE_TICK = 10;
    public static final DataTicket<Boolean> VISIBLE = DataTicket.create("cgs_projectile_visible", Boolean.class);
    public static final DataTicket<Float> YAW = DataTicket.create("cgs_projectile_yaw", Float.class);
    public static final DataTicket<Float> PITCH = DataTicket.create("cgs_projectile_pitch", Float.class);
    public static final DataTicket<Float> SCALE = DataTicket.create("cgs_projectile_scale", Float.class);

    public CgsProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ProjectileModel<>());
    }

    @Override
    public void captureDefaultRenderState(T entity, Void relatedObject, R renderState, float partialTick) {
        super.captureDefaultRenderState(entity, relatedObject, renderState, partialTick);
        var scale = 1f;

        if(entity.tickCount < MAX_SIZE_TICK) {
            scale = (entity.tickCount + partialTick) / MAX_SIZE_TICK;
        }

        renderState.addGeckolibData(VISIBLE, entity.isVisible() && entity.tickCount > 1);
        renderState.addGeckolibData(YAW, Mth.lerp(partialTick, entity.yRotO, entity.getYRot()));
        renderState.addGeckolibData(PITCH, entity.getXRot());
        renderState.addGeckolibData(SCALE, scale);
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    @Override
    public void submit(R renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        if (!renderState.getOrDefaultGeckolibData(VISIBLE, false))
            return;

        poseStack.pushPose();
        {
            var scale = renderState.getOrDefaultGeckolibData(SCALE, 1f);
            poseStack.scale(scale, scale, scale);
            poseStack.rotate(Axis.YP.rotationDegrees(renderState.getOrDefaultGeckolibData(YAW, 0f)));
            poseStack.rotate(Axis.XP.rotationDegrees(-renderState.getOrDefaultGeckolibData(PITCH, 0f)));
            super.submit(renderState, poseStack, collector, cameraState);
        }
        poseStack.popPose();
    }
}
