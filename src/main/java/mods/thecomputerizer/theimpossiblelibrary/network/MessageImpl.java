package mods.thecomputerizer.theimpossiblelibrary.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Any class that extends this is required to have a constructor with a single PacketBuffer as an input
 */
public abstract class MessageImpl {

    private final List<ServerPlayer> players = new ArrayList<>();

    public abstract EnvType getSide();

    /**
     * This needs to match what the receiver was registered as
     */
    public abstract ResourceLocation getRegistryName();

    public FriendlyByteBuf encode() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        encode(buf);
        return buf;
    }
    public abstract void encode(FriendlyByteBuf buf);

    @Environment(EnvType.CLIENT)
    public abstract void handleClient(Minecraft minecraft, ClientPacketListener listener, PacketSender response);

    public abstract void handleServer(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener,
                                      PacketSender response);

    /**
     * For packets getting sent to the client, it will send the packet to all players added
     */
    public MessageImpl addPlayers(ServerPlayer ... players) {
        Collections.addAll(this.players, players);
        return this;
    }

    public void send() {
        if(Objects.nonNull(getSide())) {
            if(getSide()==EnvType.CLIENT) {
                for(ServerPlayer player : this.players)
                    NetworkHandler.sendToPlayer(this, player);
            } else NetworkHandler.sendToServer(this);
        }
    }
}