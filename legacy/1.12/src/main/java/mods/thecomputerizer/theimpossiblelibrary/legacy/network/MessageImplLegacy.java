package mods.thecomputerizer.theimpossiblelibrary.legacy.network;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.network.MessageImplAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

public abstract class MessageImplLegacy implements MessageImplAPI<Side,EntityPlayerMP>, IMessage {

    private final Set<EntityPlayerMP> players = new HashSet<>();
    @Override
    public MessageImplAPI<?, ?> addPlayers(EntityPlayerMP ... players) {
        this.players.addAll(Arrays.asList(players));
        return this;
    }

    @Override
    public MessageImplAPI<?, ?> addPlayers(Iterable<EntityPlayerMP> players) {
        for(EntityPlayerMP player : players) this.players.add(player);
        return this;
    }

    @Override
    public <CONTEXT> void handle(CONTEXT ctx) {
        handleLegacy((MessageContext)ctx);
    }

    protected abstract void handleLegacy(MessageContext ctx);

    @Override
    public void send() {
        if(getSide().isClient()) {
            for(EntityPlayerMP player : this.players)
                NetworkHelper.sendToPlayer(this,player);
        } else NetworkHelper.sendToServer(this);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.encode(buf);
    }
}
