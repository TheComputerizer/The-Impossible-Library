package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.entity.Player1_18_2;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayer1_18_2 extends Player1_18_2<ServerPlayer> {
    
    public ServerPlayer1_18_2(Object player) {
        super(player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}
