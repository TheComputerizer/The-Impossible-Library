package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Any class that extends this is required to have a constructor with a single PacketBuffer as an input
 */
public abstract class MessageImpl {

    private final List<ServerPlayerEntity> players = new ArrayList<>();


    public abstract Dist getSide();

    public abstract void encode(PacketBuffer buf);

    /**
     * Don't call NetworkEvent.Context#setPacketHandled handled from here that gets handled automatically
     */
    public abstract void handle(NetworkEvent.Context ctx);

    /**
     * For packets getting sent to the client, it will send the packet to all players added
     */
    public MessageImpl addPlayers(ServerPlayerEntity ... players) {
        Collections.addAll(this.players, players);
        return this;
    }

    public void send() {
        if(Objects.nonNull(getSide())) {
            if(getSide().isClient()) {
                for(ServerPlayerEntity player : this.players)
                    NetworkHandler.sendToPlayer(this, player);
            } else NetworkHandler.sendToServer(this);
        }
    }
}
