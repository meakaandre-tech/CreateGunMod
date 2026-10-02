package com.nukateam.cgs.client.layers;

import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.client.animators.WeaponAnimator;
import com.nukateam.ntgl.client.render.layers.LayerBase;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

/** Draws the hammer once more with the texture of the attached head (stone, iron, diamond, netherite). */
public class HammerHeadLayer<T extends WeaponAnimator, O, R extends GeoRenderState> extends LayerBase<T, O, R> {
    public static final String PATH = "textures/weapons/hammer/";
    public static final DataTicket<Identifier> HEAD_TEXTURE = DataTicket.create("cgs_hammer_head_texture", Identifier.class);

    public HammerHeadLayer(GeoRenderer<T, O, R> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void addRenderData(T animatable, O relatedObject, R renderState, float partialTick) {
        var texture = getHeadTexture(animatable);

        if (texture != null)
            renderState.addGeckolibData(HEAD_TEXTURE, texture);
    }

    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector collector) {
        var texture = renderPassInfo.renderState().getOrDefaultGeckolibData(HEAD_TEXTURE, (Identifier) null);

        if (texture != null && renderPassInfo.willRender())
            this.renderer.submitRenderTasks(renderPassInfo, collector.order(1), RenderTypes.armorCutoutNoCull(texture));
    }

    @Nullable
    private Identifier getHeadTexture(T animatable) {
        var stack = animatable.getStack();
        if (stack == null || stack.isEmpty()) return null;

        var attachment = WeaponStateHelper.getAttachmentItem(CgsAttachmentTypes.HEAD,
                new WeaponData(stack, Minecraft.getInstance().player));

        if(!attachment.isEmpty() && attachment.getItem() instanceof HammerHeadItem head){
            var name = "hammer_" + head.getTierName() + ".png";
            return Identifier.fromNamespaceAndPath(Gunsmithing.MOD_ID, PATH + name);
        }
        return null;
    }
}
