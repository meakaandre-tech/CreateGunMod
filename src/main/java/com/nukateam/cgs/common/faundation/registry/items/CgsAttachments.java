package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.attachments.HammerHeadItem;
import com.nukateam.cgs.common.ntgl.AttachmentMods;
import com.nukateam.cgs.common.ntgl.CgsAttachmentTypes;
import com.nukateam.ntgl.common.data.holders.AttachmentType;
import com.nukateam.ntgl.common.data.attachment.impl.Barrel;
import com.nukateam.ntgl.common.data.attachment.impl.GenericAttachment;
import com.nukateam.ntgl.common.foundation.item.attachment.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import com.nukateam.ntgl.platform.DeferredRegister;
import net.minecraft.core.registries.Registries;
import com.nukateam.ntgl.platform.DeferredHolder;

import static com.nukateam.cgs.common.ntgl.AttachmentMods.*;
import static com.nukateam.cgs.common.ntgl.CgsAttachmentTypes.ENGINE;

public class CgsAttachments {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Gunsmithing.MOD_ID);

    //GENERIC
    public static final DeferredHolder<Item, Item> SCOPE = ITEMS.registerItem("scope", p -> new ScopeItem(LONG_SCOPE, p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> STOCK = ITEMS.registerItem("stock", p -> new AttachmentItem<>(AttachmentType.STOCK, GenericAttachment.create(AttachmentMods.STOCK), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> BAYONET = ITEMS.registerItem("bayonet", p -> new AttachmentItem<>(AttachmentType.MUZZLE,
                    GenericAttachment.create(AttachmentMods.BAYONET_MODIFIERS), p.stacksTo(1)));

    //FLINTLOCK
    public static final DeferredHolder<Item, Item> FLINTLOCK_LONG_BARREL = ITEMS.registerItem("flintlock_long_barrel", p -> new BarrelItem(Barrel.create(11f, AttachmentMods.LONG_BARREL), p.stacksTo(1)));
//    public static final DeferredHolder<Item, Item> FLINTLOCK_MORTAR_BARREL = ITEMS.register("flintlock_mortar_barrel",
//            () -> new BarrelItem(Barrel.create(5f, AttachmentMods.MORTAR_BARREL), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> BLUNDERBUSS_BARREL = ITEMS.registerItem("blunderbuss_barrel", p -> new BarrelItem(Barrel.create(5f, AttachmentMods.BLUNDERBUSS_BARREL), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> LONG_BLUNDERBUSS_BARREL = ITEMS.registerItem("blunderbuss_barrel_long", p -> new BarrelItem(Barrel.create(10f, AttachmentMods.LONG_BLUNDERBUSS_BARREL), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> REVOLVING_CHAMBERS = ITEMS.registerItem("flintlock_chambers", p -> new AttachmentItem<>(AttachmentType.MAGAZINE, GenericAttachment.create(AttachmentMods.REVOLVING_CHAMBERS), p.stacksTo(1)));

    //REVOLVER
    public static final DeferredHolder<Item, Item> REVOLVER_LONG_BARREL = ITEMS.registerItem("long_barrel", p -> new BarrelItem(Barrel.create(11f, AttachmentMods.REVOLVER_LONG_BARREL), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> REVOLVER_BELT = ITEMS.registerItem("round_belt", p -> new AttachmentItem<>(CgsAttachmentTypes.CHAMBER,
                    GenericAttachment.create(BELT_MODIFIERS), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> REVOLVER_AUTO = ITEMS.registerItem("auto_fire", p -> new AttachmentItem<>(CgsAttachmentTypes.FRAME,
                    GenericAttachment.create(AUTO_FIRE), p.stacksTo(1)));

    //SHOTGUN
    public static final DeferredHolder<Item, Item> SHOTGUN_DRUM = ITEMS.registerItem("shotgun_drum", p -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(SHOTGUN_DRUM_MODIFIER, SHOTGUN_MODIFIER), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> SHOTGUN_PUMP = ITEMS.registerItem("shotgun_pump", p -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(SHOTGUN_PUMP_MODIFIER, SHOTGUN_MODIFIER), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> SHOTGUN_LONG_BARREL = ITEMS.registerItem("shotgun_long_barrel", p -> new BarrelItem(Barrel.create(11f, AttachmentMods.SHOTGUN_LONG_BARREL), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> SHOTGUN_SPREAD_BARREL = ITEMS.registerItem("shotgun_spread_barrel", p -> new BarrelItem(Barrel.create(1f, AttachmentMods.SHOTGUN_SPREAD_BARREL), p.stacksTo(1)));

    //GATLING
    public static final DeferredHolder<Item, Item> STEAM_ENGINE = ITEMS.registerItem("steam_engine", p -> new AttachmentItem<>(ENGINE, GenericAttachment.create(STEAM_ENGINE_MODIFIERS), p.stacksTo(1)));
//    public static final DeferredHolder<Item, Item> IRON_SIGHT = ITEMS.register("iron_sight",
//            () -> new ScopeItem(SHORT_SCOPE, p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> GATLING_DRUM = ITEMS.registerItem("gatling_drum", p -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(GATLING_DRUM_MODIFIERS), p.stacksTo(1)));

    //NAILGUN
    public static final DeferredHolder<Item, Item> NAILGUN_SPLITTER = ITEMS.registerItem("nailgun_splitter", p -> new BarrelItem(Barrel.create(1f, AttachmentMods.NAILGUN_SPLIT_BARREL), p.stacksTo(1)));

//    //BLAZEGUN
//    public static final DeferredHolder<Item, Item> POTION_TANK = ITEMS.register("potion_tank",
//            () -> new AttachmentItem<>(CgsAttachmentTypes.FRAME,
//                    GenericAttachment.create(), p.stacksTo(1)));

    //LAUNCHER
//    public static final DeferredHolder<Item, Item> ROCKET_CONTAINER = ITEMS.register("rocket_container",
//            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
//                    GenericAttachment.create(), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> AUTO_LAUNCHER = ITEMS.registerItem("launcher_auto", p -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(AttachmentMods.AUTO_LAUNCHER), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> BALLISTAZOOKA = ITEMS.registerItem("ballistazooka", p -> new AttachmentItem<>(AttachmentType.MAGAZINE,
                    GenericAttachment.create(AttachmentMods.BALLISTAZOOKA), p.stacksTo(1)));
//    public static final DeferredHolder<Item, Item> HOOK_LAUNCHER = ITEMS.register("launcher_hook",
//            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
//                    GenericAttachment.create(), p.stacksTo(1)));
//    public static final DeferredHolder<Item, Item> ITEM_LAUNCHER = ITEMS.register("item_launcher",
//            () -> new AttachmentItem<>(AttachmentType.MAGAZINE,
//                    GenericAttachment.create(), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> LAUNCHER_BAYONET = ITEMS.registerItem("big_bayonet", p -> new AttachmentItem<>(AttachmentType.MUZZLE,
                    GenericAttachment.create(AttachmentMods.BIG_BAYONET), p.stacksTo(1)));

    //HAMMER
    public static final DeferredHolder<Item, Item> HAMMER_CHAMBER = ITEMS.registerItem("hammer_chamber", p -> new AttachmentItem<>(AttachmentType.MAGAZINE, GenericAttachment.create(RECIEVER), p.stacksTo(1)));
    public static final DeferredHolder<Item, Item> HAMMER_STONE = ITEMS.registerItem("hammer_stone", p -> new HammerHeadItem(ToolMaterial.STONE, "stone", HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), p));
    public static final DeferredHolder<Item, Item> HAMMER_IRON = ITEMS.registerItem("hammer_iron", p -> new HammerHeadItem(ToolMaterial.IRON, "iron", HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), p));
    public static final DeferredHolder<Item, Item> HAMMER_DIAMOND = ITEMS.registerItem("hammer_diamond", p -> new HammerHeadItem(ToolMaterial.DIAMOND, "diamond", HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), p));
    public static final DeferredHolder<Item, Item> HAMMER_NETHERITE = ITEMS.registerItem("hammer_netherite", p -> new HammerHeadItem(ToolMaterial.NETHERITE, "netherite", HammerHeadItem.Type.HAMMER, GenericAttachment.create(HAMMER_HEAD), p));
    public static final DeferredHolder<Item, Item> AXE_STONE = ITEMS.registerItem("axe_stone", p -> new HammerHeadItem(ToolMaterial.STONE, "stone", HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), p));
    public static final DeferredHolder<Item, Item> AXE_IRON = ITEMS.registerItem("axe_iron", p -> new HammerHeadItem(ToolMaterial.IRON, "iron", HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), p));
    public static final DeferredHolder<Item, Item> AXE_DIAMOND = ITEMS.registerItem("axe_diamond", p -> new HammerHeadItem(ToolMaterial.DIAMOND, "diamond", HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), p));
    public static final DeferredHolder<Item, Item> AXE_NETHERITE = ITEMS.registerItem("axe_netherite", p -> new HammerHeadItem(ToolMaterial.NETHERITE, "netherite", HammerHeadItem.Type.AXE, GenericAttachment.create(AXE_HEAD), p));

    public static void register() {
    }
}
