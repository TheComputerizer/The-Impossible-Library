package mods.thecomputerizer.theimpossiblelibrary.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MessageImpl implements IMessage {

    private final List<EntityPlayerMP> players = new ArrayList<>();

    public abstract IMessage handle(MessageContext ctx);

    public abstract Side getSide();

    /**
     * For packets getting sent to the client, it will send the packet to all players added
     */
    public MessageImpl addPlayers(EntityPlayerMP ... players) {
        Collections.addAll(this.players, players);
        return this;
    }

    public void send() {
        if(getSide().isClient()) {
            for(EntityPlayerMP player : this.players)
                NetworkHandler.sendToPlayer(this, player);
        } else NetworkHandler.sendToServer(this);
    }
}
