package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.entity.PlayerFabric1_16_5;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayerFabric1_16_5 extends PlayerFabric1_16_5<ServerPlayer> {
    
    public ServerPlayerFabric1_16_5(ServerPlayer player) {
        super(player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}