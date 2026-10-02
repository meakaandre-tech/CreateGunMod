package com.nukateam.cgs.client.renderers.weapon;

import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.Minecraft;

public class FlintlockRenderer extends BaseWeaponRenderer {
    public FlintlockRenderer() {
        super();
    }

    @Override
    protected void updateBone(RenderPassInfo<GeoRenderState> renderPassInfo, GeoBone bone, BoneSnapshots snapshots) {
        super.updateBone(renderPassInfo, bone, snapshots);
        var name = bone.name();

        if (name.equals("scope") && hasRevolvingChambersEquiped()) {
            var snapshot = snapshots.get(bone);
            snapshot.setTranslateZ(snapshot.getTranslateZ() - 3);
        }

        if (name.equals("magazine") && hasBlunderbussEquiped()) {
            var scale = 1.3f;
            var snapshot = snapshots.get(bone);
            snapshot.setScale(snapshot.getScaleX() * scale, snapshot.getScaleY() * scale, snapshot.getScaleZ() * scale);
            snapshot.setTranslateY(snapshot.getTranslateY() - 2.2f);
        }

        if (name.equals("melee2")) {
            var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, new WeaponData(gunStack, Minecraft.getInstance().player));

            if(barrel.getItem() == CgsAttachments.FLINTLOCK_LONG_BARREL.get())
                setHidden(snapshots, bone, true);
        }
    }

    private boolean hasRevolvingChambersEquiped() {
        return WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE,
                new WeaponData(gunStack, Minecraft.getInstance().player)).getItem() == CgsAttachments.REVOLVING_CHAMBERS.get();
    }

    private boolean hasBlunderbussEquiped() {
        var barrel = WeaponStateHelper.getAttachmentItem(AttachmentType.BARREL, new WeaponData(gunStack, Minecraft.getInstance().player)).getItem();
        return barrel == CgsAttachments.BLUNDERBUSS_BARREL.get() && barrel == CgsAttachments.LONG_BLUNDERBUSS_BARREL.get();
    }
}
