package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Player1_16_5;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayer1_16_5 extends Player1_16_5<ServerPlayer> {
    
    public ServerPlayer1_16_5(Object player) {
        super(player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}
