package com.nukateam.cgs.common.network;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.network.packets.C2SMessageFuel;
import com.nukateam.ntgl.platform.SubscribeEvent;
import com.nukateam.ntgl.platform.IPayloadContext;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PacketHandler {
    /** Payload types and server receivers; called from the mod initializer on both sides. */
    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(C2SMessageFuel.TYPE, C2SMessageFuel.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SMessageFuel.TYPE, (packet, ctx) ->
                C2SMessageFuel.handle(packet, new IPayloadContext(ctx.player(), ctx.server()::execute)));
    }
}
