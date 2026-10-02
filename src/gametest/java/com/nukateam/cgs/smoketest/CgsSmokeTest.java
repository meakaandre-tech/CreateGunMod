package com.nukateam.cgs.smoketest;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.ntgl.CgsAmmoHolders;
import com.nukateam.ntgl.client.input.NtglKeyBinds;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.client.util.handler.ClientShootingHandler;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestServerContext;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.lwjgl.glfw.GLFW;

import java.util.Set;
import java.util.TreeSet;

/**
 * CI smoke test (not shipped): opens a singleplayer world, holds every weapon, reloads, shoots, opens the
 * screens and takes screenshots so the port can be checked without a local game; then repeats the basics
 * against a dedicated server.
 */
public class CgsSmokeTest implements FabricClientGameTest {
    private static final String[] WEAPONS = {"revolver", "flintlock", "shotgun", "nailgun", "gatling", "blazegun", "launcher", "hammer", "frag_grenade"};

    @Override
    public void runTest(ClientGameTestContext context) {
        try (var singleplayer = context.worldBuilder().create()) {
            var connection = singleplayer.getConnection();
            var server = singleplayer.getServer();

            context.getInput().resizeWindow(1280, 720);
            connection.waitForChunksRender();
            server.runCommand("gamemode creative @a");
            server.runCommand("time set noon");
            server.runCommand("weather clear");
            arena(context, server);

            step("models", () -> context.runOnClient(mc -> {
                log("animation ids: " + com.geckolib.cache.GeckoLibResources.getBakedAnimations().cache().keySet().stream()
                        .filter(id -> id.getNamespace().equals("cgs")).sorted().toList());
                log("model ids: " + com.geckolib.cache.GeckoLibResources.getBakedModels().cache().keySet().stream()
                        .filter(id -> id.getNamespace().equals("cgs")).sorted().toList());
            }));

            for (var weapon : WEAPONS) {
                step(weapon, () -> {
                    arena(context, server);
                    hold(context, server, weapon);
                    context.waitTicks(25);
                    context.takeScreenshot("10_" + weapon + "_1_first_person");

                    // creative reload, then attack a zombie standing in front of the wall
                    context.getInput().holdKeyFor(NtglKeyBinds.KEY_RELOAD, 4);
                    context.waitTicks(20);
                    context.takeScreenshot("10_" + weapon + "_2_reloading");
                    context.waitTicks(100);
                    at(server, "summon minecraft:zombie {x+0.5} 150 {z+4.5} {NoAI:1b,PersistenceRequired:1b,Rotation:[180f,0f]}");
                    context.waitTicks(5);
                    context.runOnClient(mc -> logState(mc, weapon + ": before attack"));
                    context.getInput().holdMouse(0);
                    var seen = watchEntities(context, 6);
                    context.takeScreenshot("10_" + weapon + "_3_attack");
                    seen.addAll(watchEntities(context, 24));
                    context.getInput().releaseMouse(0);
                    seen.addAll(watchEntities(context, 50));
                    context.takeScreenshot("10_" + weapon + "_4_after");
                    log(weapon + ": entities seen " + seen);
                    context.runOnClient(mc -> logState(mc, weapon + ": after attack"));
                    logLiving(server, weapon);
                    server.runCommand("kill @e[type=!minecraft:player]");
                    server.runCommand("effect clear @a");
                    context.waitTicks(10);
                });
            }

            step("survival ammo", () -> {
                arena(context, server);
                hold(context, server, "revolver");
                server.runCommand("gamemode survival @a");
                server.runCommand("give @a cgs:round_revolver 20");
                context.waitTicks(10);
                context.runOnClient(mc -> logState(mc, "survival: before reload, rounds " + rounds(mc)));
                context.getInput().holdKeyFor(NtglKeyBinds.KEY_RELOAD, 4);
                context.waitTicks(420);
                context.runOnClient(mc -> logState(mc, "survival: after reload, rounds " + rounds(mc)));
                for (int i = 0; i < 3; i++) {
                    context.getInput().holdMouse(0);
                    context.waitTicks(3);
                    context.getInput().releaseMouse(0);
                    context.waitTicks(15);
                }
                context.runOnClient(mc -> logState(mc, "survival: after three shots, rounds " + rounds(mc)));
                server.runCommand("gamemode creative @a");
                server.runCommand("clear @a cgs:round_revolver");
                context.waitTicks(5);
            });

            step("aim", () -> {
                arena(context, server);
                hold(context, server, "revolver");
                context.waitTicks(20);
                context.getInput().holdMouse(1);
                context.waitTicks(15);
                context.takeScreenshot("20_revolver_aiming");
                context.getInput().releaseMouse(1);
                context.waitTicks(10);
            });

            step("fuel", () -> {
                arena(context, server);
                hold(context, server, "blazegun");
                attachEngine(server);
                server.runCommand("item replace entity @a weapon.offhand with minecraft:water_bucket");
                server.runCommand("gamemode survival @a");
                context.waitTicks(10);
                context.takeScreenshot("21_blazegun_with_engine");
                context.runOnClient(mc -> log("fuel: water before " + FuelUtils.getFuel(mc.player.getMainHandItem(), CgsAmmoHolders.WATER)
                        + " offhand " + mc.player.getOffhandItem()));
                context.getInput().holdMouse(1);
                context.waitTicks(3);
                context.getInput().releaseMouse(1);
                context.waitTicks(15);
                context.runOnClient(mc -> log("fuel: water after " + FuelUtils.getFuel(mc.player.getMainHandItem(), CgsAmmoHolders.WATER)
                        + " offhand " + mc.player.getOffhandItem()));
                server.runCommand("gamemode creative @a");
                server.runCommand("item replace entity @a weapon.offhand with minecraft:air");
                server.runCommand("kill @e[type=!minecraft:player]");
                context.waitTicks(5);
            });

            step("third person", () -> {
                for (var weapon : new String[]{"revolver", "gatling", "hammer"}) {
                    arena(context, server);
                    hold(context, server, weapon);
                    // the arm pose of the weapon is only applied once the walk animation has started
                    context.getInput().holdKeyFor(options -> options.keyUp, 4);
                    context.waitTicks(10);
                    context.runOnClient(mc -> mc.options.setCameraType(CameraType.THIRD_PERSON_BACK));
                    context.waitTicks(10);
                    context.takeScreenshot("30_" + weapon + "_third_person_back");
                    context.runOnClient(mc -> mc.options.setCameraType(CameraType.THIRD_PERSON_FRONT));
                    context.waitTicks(10);
                    context.takeScreenshot("30_" + weapon + "_third_person_front");
                    context.runOnClient(mc -> mc.options.setCameraType(CameraType.FIRST_PERSON));
                    context.waitTicks(5);
                }
            });

            step("inventory", () -> {
                arena(context, server);
                server.runCommand("clear @a");
                server.runCommand("gamemode survival @a");
                for (var item : new String[]{"revolver", "flintlock", "shotgun", "nailgun", "gatling", "blazegun", "launcher", "hammer", "frag_grenade",
                        "scope", "stock", "bayonet", "steam_engine", "gatling_drum", "hammer_iron", "axe_diamond", "ballistazooka", "round_belt",
                        "round_revolver", "round_shotgun", "round_gatling", "paper_cartridge", "nail", "rocket", "spear", "lead_balls",
                        "tank_empty", "tank_water", "tank_lava", "lead_ingot", "steel_ingot", "steel_sheet", "niter", "sulfur", "guano",
                        "lead_ore", "deepslate_lead_ore", "sulfur_ore", "raw_lead_block", "lead_block", "steel_block", "press_form_gatling"})
                    server.runCommand("give @a cgs:" + item);
                context.waitTicks(10);
                context.getInput().pressKey(options -> options.keyInventory);
                context.waitTicks(10);
                context.takeScreenshot("40_inventory");
                context.getInput().pressKey(GLFW.GLFW_KEY_ESCAPE);
                server.runCommand("gamemode creative @a");
                context.waitTicks(5);
            });

            step("attachments screen", () -> {
                context.getInput().pressKey(NtglKeyBinds.KEY_ATTACHMENTS);
                context.waitTicks(15);
                context.takeScreenshot("41_attachments_screen");
                context.getInput().pressKey(GLFW.GLFW_KEY_ESCAPE);
                context.waitTicks(5);
            });

            step("blocks and dropped items", () -> {
                arena(context, server);
                at(server, "setblock {x-2} 150 {z+3} cgs:lead_ore");
                at(server, "setblock {x-1} 150 {z+3} cgs:deepslate_lead_ore");
                at(server, "setblock {x+0} 150 {z+3} cgs:sulfur_ore");
                at(server, "setblock {x+1} 150 {z+3} cgs:raw_lead_block");
                at(server, "setblock {x+2} 150 {z+3} cgs:lead_block");
                at(server, "setblock {x-2} 151 {z+3} cgs:steel_block");
                at(server, "setblock {x-1} 151 {z+3} cgs:guano_block[layers=3]");
                at(server, "summon item {x+1.5} 152 {z+3.5} {Item:{id:\"cgs:gatling\",count:1},NoGravity:1b}");
                at(server, "summon item {x+0.5} 152 {z+3.5} {Item:{id:\"cgs:revolver\",count:1},NoGravity:1b}");
                context.waitTicks(20);
                context.takeScreenshot("42_blocks_and_dropped_items");
            });
        }

        // Second pass against a real dedicated server: unlike singleplayer, every packet is encoded and decoded.
        try (var dedicated = context.worldBuilder().createServer(); var connection = dedicated.connect()) {
            connection.waitForChunksRender();
            hasBase = false;
            dedicated.runCommand("gamemode creative @a");
            dedicated.runCommand("time set noon");
            arena(context, dedicated);

            for (var weapon : new String[]{"revolver", "launcher", "blazegun"}) {
                step("dedicated " + weapon, () -> {
                    arena(context, dedicated);
                    hold(context, dedicated, weapon);
                    context.waitTicks(20);
                    context.getInput().holdKeyFor(NtglKeyBinds.KEY_RELOAD, 4);
                    context.waitTicks(100);
                    at(dedicated, "summon minecraft:zombie {x+0.5} 150 {z+4.5} {NoAI:1b,PersistenceRequired:1b,Rotation:[180f,0f]}");
                    context.waitTicks(10);
                    context.getInput().holdMouse(0);
                    var seen = watchEntities(context, 30);
                    context.getInput().releaseMouse(0);
                    context.takeScreenshot("50_dedicated_" + weapon);
                    seen.addAll(watchEntities(context, 30));
                    log("dedicated " + weapon + ": entities seen " + seen);
                    context.runOnClient(mc -> logState(mc, "dedicated " + weapon + ": after attack"));
                    logLiving(dedicated, "dedicated " + weapon);
                    dedicated.runCommand("kill @e[type=!minecraft:player]");
                    context.waitTicks(5);
                });
            }

            step("dedicated fuel", () -> {
                arena(context, dedicated);
                hold(context, dedicated, "blazegun");
                attachEngine(dedicated);
                dedicated.runCommand("item replace entity @a weapon.offhand with minecraft:water_bucket");
                dedicated.runCommand("gamemode survival @a");
                context.waitTicks(10);
                context.getInput().holdMouse(1);
                context.waitTicks(3);
                context.getInput().releaseMouse(1);
                context.waitTicks(15);
                context.runOnClient(mc -> log("dedicated fuel: water after " + FuelUtils.getFuel(mc.player.getMainHandItem(), CgsAmmoHolders.WATER)
                        + " offhand " + mc.player.getOffhandItem()));
            });
        }
    }

    private static int baseX, baseZ;
    private static boolean hasBase;

    /** Runs a command with x/z coordinates relative to the arena: "{x+N}" and "{z+N}" are replaced. */
    private static void at(TestServerContext server, String command) {
        var matcher = java.util.regex.Pattern.compile("\\{([xz])([+-][0-9.]+)\\}").matcher(command);
        var result = new StringBuilder();
        while (matcher.find()) {
            var value = (matcher.group(1).equals("x") ? baseX : baseZ) + Double.parseDouble(matcher.group(2));
            matcher.appendReplacement(result, value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value));
        }
        matcher.appendTail(result);
        server.runCommand(result.toString());
    }

    /** A platform high above the ground with a wall to shoot at; rebuilt before every step, the player looks at the wall. */
    private static void arena(ClientGameTestContext context, TestServerContext server) {
        if (!hasBase) {
            server.runOnServer(minecraftServer -> {
                var position = minecraftServer.getPlayerList().getPlayers().getFirst().blockPosition();
                baseX = position.getX();
                baseZ = position.getZ();
            });
            hasBase = true;
        }
        server.runCommand("kill @e[type=!minecraft:player]");
        at(server, "fill {x-7} 149 {z-3} {x+7} 149 {z+13} minecraft:stone");
        at(server, "fill {x-7} 150 {z-3} {x+7} 156 {z+13} minecraft:air");
        at(server, "fill {x-5} 150 {z+10} {x+5} 156 {z+10} minecraft:smooth_stone");
        at(server, "tp @a {x+0.5} 150 {z+0.5} 0 0");
        context.waitTicks(5);
    }

    private static void attachEngine(TestServerContext server) {
        server.runOnServer(minecraftServer -> {
            var player = minecraftServer.getPlayerList().getPlayers().getFirst();
            var stack = player.getMainHandItem();
            WeaponStateHelper.writeAttachments(java.util.List.of(new net.minecraft.world.item.ItemStack(
                    com.nukateam.cgs.common.faundation.registry.items.CgsAttachments.STEAM_ENGINE.get())), new WeaponData(stack, player));
        });
    }

    private static int rounds(Minecraft mc) {
        return mc.player.getInventory().countItem(com.nukateam.cgs.common.faundation.registry.items.CgsAmmo.REVOLVER_ROUND.get());
    }

    private static void logLiving(TestServerContext server, String when) {
        server.runOnServer(minecraftServer -> {
            var level = minecraftServer.getPlayerList().getPlayers().getFirst().level();
            for (var entity : level.getAllEntities())
                if (entity instanceof net.minecraft.world.entity.LivingEntity living && !(entity instanceof net.minecraft.world.entity.player.Player))
                    log(when + ": server entity " + entity.getType().toShortString() + " health " + living.getHealth() + " on fire " + entity.isOnFire());
        });
    }

    private static void log(String message) {
        Gunsmithing.LOGGER.info("[smoke] {}", message);
    }

    /** Collects the types of the entities the client sees during the given number of ticks. */
    private static Set<String> watchEntities(ClientGameTestContext context, int ticks) {
        var seen = new TreeSet<String>();
        for (int i = 0; i < ticks; i++) {
            context.waitTick();
            context.runOnClient(mc -> {
                for (var entity : mc.level.entitiesForRendering())
                    seen.add(entity.getType().toShortString());
            });
        }
        return seen;
    }

    private static void logState(Minecraft mc, String when) {
        var stack = mc.player.getMainHandItem();
        var data = new WeaponData(stack, mc.player);
        log(when + ": held " + stack.getItem() + " ammo " + WeaponStateHelper.getAmmoCount(data)
                + " shooting " + ClientShootingHandler.get().isShooting(mc.player, InteractionHand.MAIN_HAND)
                + " reloading " + ClientReloadHandler.get().isReloading(mc.player, InteractionHand.MAIN_HAND)
                + " screen " + mc.gui.screen());
    }

    private static void hold(ClientGameTestContext context, TestServerContext server, String weapon) {
        server.runCommand("item replace entity @a weapon.mainhand with cgs:" + weapon);
        context.waitTicks(5);
    }

    /** A failing step must not hide the results of the following ones. */
    private static void step(String name, Runnable action) {
        try {
            action.run();
        } catch (Throwable throwable) {
            System.err.println("[CGS smoke test] step '" + name + "' failed: " + throwable);
            throwable.printStackTrace();
        }
    }
}
