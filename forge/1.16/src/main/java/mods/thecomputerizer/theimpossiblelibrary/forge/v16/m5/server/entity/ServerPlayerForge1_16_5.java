package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.PlayerForge1_16_5;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ServerPlayerForge1_16_5 extends PlayerForge1_16_5<ServerPlayerEntity> {
    
    public ServerPlayerForge1_16_5(Object player) {
        super((ServerPlayerEntity)player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}