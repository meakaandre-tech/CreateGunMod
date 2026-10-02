package com.nukateam.cgs.smoketest;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.CgsAmmo;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.holders.WeaponMode;
import com.nukateam.ntgl.common.network.ServerPlayHandler;
import com.nukateam.ntgl.common.network.message.weapon.C2SMessageReload;
import com.nukateam.ntgl.common.network.message.weapon.C2SMessageShoot;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * CI smoke test (not shipped), run on a real dedicated server: a mock player shoots and reloads the weapons
 * through the same server handlers the network packets use, and the loaded recipes are counted. It fails on
 * any exception, which is how a client-only class referenced from server code would show up.
 */
public class CgsServerGameTest {
    @GameTest(maxTicks = 900)
    public void weaponsOnDedicatedServer(GameTestHelper helper) {
        var player = helper.makeMockServerPlayerInLevel();
        var position = helper.absoluteVec(new Vec3(1.5, 2.0, 1.5));
        player.setGameMode(GameType.SURVIVAL);
        player.snapTo(position.x, position.y, position.z, 0F, 0F);

        logRecipes(helper);

        var zombie = helper.spawnWithNoFreeWill(EntityTypes.ZOMBIE, new Vec3(1.5, 2.0, 5.5));
        var seen = new TreeSet<String>();

        // the mock player has no real connection, so nothing ticks it: do that by hand
        for (int tick = 1; tick < 700; tick++)
            helper.runAfterDelay(tick, player::doTick);

        List<Item> weapons = List.of(CgsWeapons.REVOLVER.get(), CgsWeapons.SHOTGUN.get(), CgsWeapons.FLINTLOCK.get(),
                CgsWeapons.GATLING.get(), CgsWeapons.NAILGUN.get(), CgsWeapons.LAUNCHER.get(), CgsWeapons.BLAZEGUN.get());
        int tick = 2;

        for (var weapon : weapons) {
            helper.runAfterDelay(tick, () -> {
                zombie.setHealth(zombie.getMaxHealth());
                var stack = new ItemStack(weapon);
                player.setItemInHand(InteractionHand.MAIN_HAND, stack);
                WeaponStateHelper.setAmmoCount(new WeaponData(stack, player), 10);
                seen.clear();
                log(weapon + ": before shooting ammo " + ammo(player));
                ServerPlayHandler.handleShoot(new C2SMessageShoot(player.getId(), 0F, 0F, 0F, 0F, InteractionHand.MAIN_HAND, WeaponMode.PRIMARY), player);
                collect(helper, seen);
            });
            for (int i = 1; i < 30; i += 2)
                helper.runAfterDelay(tick + i, () -> collect(helper, seen));
            helper.runAfterDelay(tick + 30, () ->
                    log(weapon + ": after shooting ammo " + ammo(player) + " zombie health " + zombie.getHealth() + " entities seen " + seen));
            tick += 40;
        }

        helper.runAfterDelay(tick, () -> {
            var revolver = new ItemStack(CgsWeapons.REVOLVER.get());
            player.setItemInHand(InteractionHand.MAIN_HAND, revolver);
            WeaponStateHelper.setAmmoCount(new WeaponData(revolver, player), 0);
            player.getInventory().add(new ItemStack(CgsAmmo.REVOLVER_ROUND.get(), 10));
            log("before reloading: ammo " + ammo(player) + " rounds in inventory " + player.getInventory().countItem(CgsAmmo.REVOLVER_ROUND.get()));
            ServerPlayHandler.handleReload(new C2SMessageReload(InteractionHand.MAIN_HAND, WeaponMode.PRIMARY), player);
        });

        helper.runAfterDelay(tick + 200, () -> {
            log("after reloading: ammo " + ammo(player) + " rounds in inventory " + player.getInventory().countItem(CgsAmmo.REVOLVER_ROUND.get()));
            player.discard();
            helper.succeed();
        });
    }

    private static void logRecipes(GameTestHelper helper) {
        var counts = new TreeMap<String, Integer>();

        for (var holder : helper.getLevel().getServer().getRecipeManager().getRecipes()) {
            var id = holder.id().identifier();
            var result = String.valueOf(holder.value().getType());

            if (id.getNamespace().equals(Gunsmithing.MOD_ID) || id.getPath().contains("cgs"))
                counts.merge(result, 1, Integer::sum);
        }

        log("recipes by type: " + counts);
    }

    private static int ammo(ServerPlayer player) {
        return WeaponStateHelper.getAmmoCount(new WeaponData(player.getMainHandItem(), player));
    }

    private static void collect(GameTestHelper helper, TreeSet<String> seen) {
        for (var entity : helper.getLevel().getAllEntities())
            seen.add(entity.getType().toShortString());
    }

    private static void log(String message) {
        Gunsmithing.LOGGER.info("[server-smoke] {}", message);
    }
}
