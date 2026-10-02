package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import com.nukateam.ntgl.platform.PlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

public class HammerItem extends CgsGunItem {
    public HammerItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    public static boolean isPowered(WeaponData data){
        var ammoPerShot = WeaponModifierHelper.getAmmoPerShot(data);
        return WeaponStateHelper.isAmmoIgnored(data) || WeaponStateHelper.getAmmoCount(data) >= ammoPerShot;
    }

    @Override
    public Component getName(ItemStack stack) {
        if (PlatformHelper.isClient() && isClientAxe(stack)) {
            return Component.translatable("item.cgs.axe");
        }
        return super.getName(stack);
    }
    @Environment(EnvType.CLIENT)
    private boolean isClientAxe(ItemStack stack) {
        var headAttachment = WeaponStateHelper.getAttachmentItem(
                CgsAttachmentTypes.HEAD,
                new WeaponData(stack, Minecraft.getInstance().player)
        ).getItem();

        if (headAttachment instanceof HammerHeadItem item
                && item.getHeadType() == HammerHeadItem.Type.AXE) {
            return true;
        }

        return false;
    }
}