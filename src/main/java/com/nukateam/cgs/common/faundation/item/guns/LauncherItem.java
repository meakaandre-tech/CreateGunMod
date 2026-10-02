package com.nukateam.cgs.common.faundation.item.guns;

import com.nukateam.cgs.common.faundation.registry.items.CgsAttachments;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.util.interfaces.IWeaponModifier;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import com.nukateam.ntgl.platform.PlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;

public class LauncherItem extends CgsGunItem {
    public LauncherItem(Properties properties, IWeaponModifier... modifiers) {
        super(properties, modifiers);
    }

    @Override
    public Component getName(ItemStack stack) {
        if (PlatformHelper.isClient() && isClientBallistazooka(stack)) {
            return Component.translatable("item.cgs.ballistazooka");
        }
        return super.getName(stack);
    }
    @Environment(EnvType.CLIENT)
    private boolean isClientBallistazooka(ItemStack stack) {
        var magazineAttachment = WeaponStateHelper.getAttachmentItem(AttachmentType.MAGAZINE,
                new WeaponData(stack, Minecraft.getInstance().player)).getItem();

        if(magazineAttachment == CgsAttachments.BALLISTAZOOKA.get()){
            return true;
        }

        return false;
    }
}