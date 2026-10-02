
package com.nukateam.cgs.common.faundation.registry.items;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import net.minecraft.world.item.Item;
import com.nukateam.ntgl.platform.DeferredRegister;
import net.minecraft.core.registries.Registries;
import com.nukateam.ntgl.platform.DeferredHolder;

public class CgsAmmo {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Gunsmithing.MOD_ID);
    public static final DeferredHolder<Item, Item> GATLING_SHELL = registerItem("shell_gatling");
    public static final DeferredHolder<Item, Item> GATLING_ROUND_BLANK = registerAmmo("round_gatling_blank");
    public static final DeferredHolder<Item, Item> GATLING_ROUND = registerAmmo("round_gatling");
    public static final DeferredHolder<Item, Item> GATLING_ROUND_PIERCING = registerAmmo("round_gatling_piercing");
    public static final DeferredHolder<Item, Item> REVOLVER_SHELL = registerItem("shell_revolver");
    public static final DeferredHolder<Item, Item> REVOLVER_ROUND_BLANK = registerAmmo("round_revolver_blank");
    public static final DeferredHolder<Item, Item> REVOLVER_ROUND = registerAmmo("round_revolver");
    public static final DeferredHolder<Item, Item> REVOLVER_ROUND_PIERCING = registerAmmo("round_revolver_piercing");
    public static final DeferredHolder<Item, Item> SHOTGUN_SHELL = registerItem("shell_shotgun");
    public static final DeferredHolder<Item, Item> SHOTGUN_ROUND_BLANK = registerAmmo("round_shotgun_blank");
    public static final DeferredHolder<Item, Item> SHOTGUN_ROUND = registerAmmo("round_shotgun");
    public static final DeferredHolder<Item, Item> SHOTGUN_ROUND_INCENDIARY = registerAmmo("round_shotgun_incendiary");
    public static final DeferredHolder<Item, Item> SHOTGUN_ROUND_FLECHETTE = registerAmmo("round_shotgun_flechette");
    public static final DeferredHolder<Item, Item> SHOTGUN_ROUND_FLECHETTE_STEEL = registerAmmo("round_shotgun_flechette_steel");
    public static final DeferredHolder<Item, Item> PAPER_CARTRIDGE = registerAmmo("paper_cartridge");
    public static final DeferredHolder<Item, Item> PAPER_SHOT = registerAmmo("paper_shot");
    public static final DeferredHolder<Item, Item> NAIL = registerAmmo("nail");
    public static final DeferredHolder<Item, Item> STEEL_NAIL = registerAmmo("nail_steel");
    public static final DeferredHolder<Item, Item> ROCKET = registerAmmo("rocket");
    public static final DeferredHolder<Item, Item> SMALL_ROCKET = registerAmmo("rocket_small");
    public static final DeferredHolder<Item, Item> SPEAR = registerAmmo("spear");
    public static final DeferredHolder<Item, Item> LEAD_BALLS = registerAmmo("lead_balls");
//    public static final DeferredHolder<Item, Item> FLECHETTE = registerAmmo("flechette");
//    public static final DeferredHolder<Item, Item> FLECHETTE_STEEL = registerAmmo("flechette_steel");
//    public static final DeferredHolder<Item, Item> PAPER_CARTRIDGE_BLANK = registerAmmo("paper_cartridge_blank");
//    public static final DeferredHolder<Item, Item> NAIL_PIERCING = registerAmmo("nail_piercing");

    public static DeferredHolder<Item, Item> registerAmmo(String name) {
        return ITEMS.registerItem(name, AmmoItem::new);
    }

    
    public static DeferredHolder<Item, Item> registerItem(String name) {
        return ITEMS.registerItem(name, Item::new);
    }

    public static void register() {
    }
}
