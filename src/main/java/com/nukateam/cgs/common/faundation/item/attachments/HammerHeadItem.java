package com.nukateam.cgs.common.faundation.item.attachments;

import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class HammerHeadItem extends AttachmentItem<GenericAttachment> {
    private final ToolMaterial tier;
    private final String tierName;
    private final Type type;

    public HammerHeadItem(ToolMaterial tier, String tierName, Type type, GenericAttachment attachmentData, Properties properties) {
        super(CgsAttachmentTypes.HEAD, attachmentData, properties.durability(tier.durability() * 9).repairable(tier.repairItems()));
        this.tierName = tierName;
        this.tier = tier;
        this.type = type;
    }

    /** Lower-case name of the material, used for the head texture (hammer_NAME.png). */
    public String getTierName() {
        return tierName;
    }

    public ToolMaterial getTier() {
        return tier;
    }

    public Type getHeadType() {
        return type;
    }

    public enum Type {
        HAMMER,
        AXE
    }
}
