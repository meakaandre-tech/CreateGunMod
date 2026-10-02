
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.item.FluidContainerItem;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import com.nukateam.ntgl.platform.DeferredRegister;
import net.minecraft.core.registries.Registries;
import com.nukateam.ntgl.platform.DeferredHolder;

import java.util.Map;
import java.util.function.Function;

public class CgsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Gunsmithing.MOD_ID);
    public static final DeferredHolder<Item, Item> EMPTY_CONTAINER = ITEMS.registerItem("tank_empty",
            p -> new FluidContainerItem(Fluids.EMPTY, p.stacksTo(16)));
    public static final DeferredHolder<Item, Item> WATER_CONTAINER = ITEMS.registerItem("tank_water",
            p -> new FluidContainerItem(Fluids.WATER, p.stacksTo(16).craftRemainder(EMPTY_CONTAINER.get())));
    public static final DeferredHolder<Item, Item> LAVA_CONTAINER = ITEMS.registerItem("tank_lava",
            p -> new FluidContainerItem(Fluids.LAVA, p.stacksTo(16).craftRemainder(EMPTY_CONTAINER.get())));

    public static Map<Fluid, DeferredHolder<Item, Item>> CONTAINERS = Map.of(
            Fluids.WATER, WATER_CONTAINER,
            Fluids.LAVA, LAVA_CONTAINER
    );
    public static final DeferredHolder<Item, Item> PRESS_FORM_GATLING = registerItem("press_form_gatling");
    public static final DeferredHolder<Item, Item> PRESS_FORM_REVOLVER = registerItem("press_form_revolver");
    public static final DeferredHolder<Item, Item> PRESS_FORM_SHOTGUN = registerItem("press_form_shotgun");
    public static final DeferredHolder<Item, Item> LEAD_INGOT = registerItem ("lead_ingot");
    public static final DeferredHolder<Item, Item> STEEL_INGOT = registerItem ("steel_ingot");
    public static final DeferredHolder<Item, Item> LEAD_NUGGET = registerItem("lead_nugget");
    public static final DeferredHolder<Item, Item> STEEL_NUGGET = registerItem ("steel_nugget");
    public static final DeferredHolder<Item, Item> STEEL_SHEET = registerItem ("steel_sheet");
    public static final DeferredHolder<Item, Item> RAW_LEAD = registerItem("raw_lead");
    public static final DeferredHolder<Item, Item> NITER = registerItem ("niter");
    public static final DeferredHolder<Item, Item> SULFUR = registerItem ("sulfur");
    public static final DeferredHolder<Item, Item> CHARCOAL_DUST = registerItem ("charcoal_dust");
    public static final DeferredHolder<Item, Item> GUANO = ITEMS.registerItem("guano",
            p -> new BlockItem(CgsBlocks.GUANO_BLOCK.get(), p.useItemDescriptionPrefix()));


    
    public static DeferredHolder<Item, Item> registerItem(String name) {
        return ITEMS.registerItem(name, Item::new);
    }

    public static void register() {
    }
}
