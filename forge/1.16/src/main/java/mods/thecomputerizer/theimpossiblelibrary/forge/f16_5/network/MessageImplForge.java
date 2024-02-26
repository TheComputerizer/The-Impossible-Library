package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.network;

import mods.thecomputerizer.theimpossiblelibrary.api.network.MessageImplAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class MessageImplForge implements MessageImplAPI<Dist,ServerPlayerEntity> {

    private final Set<ServerPlayerEntity> players = new HashSet<>();
    @Override
    public MessageImplAPI<?,?> addPlayers(ServerPlayerEntity ... players) {
        this.players.addAll(Arrays.asList(players));
        return this;
    }

    @Override
    public MessageImplAPI<?,?> addPlayers(Iterable<ServerPlayerEntity> players) {
        for(ServerPlayerEntity player : players) this.players.add(player);
        return this;
    }

    @Override
    public <CONTEXT> void handle(CONTEXT ctx) {
        handleForge((Context)ctx);
    }

    protected abstract void handleForge(Context ctx);

    @Override
    public void send() {
        if(getSide().isClient()) {
            for(ServerPlayerEntity player : this.players)
                NetworkHelper.sendToPlayer(this,player);
        } else NetworkHelper.sendToServer(this);
    }
}
