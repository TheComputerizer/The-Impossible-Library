package mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Player1_21;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayer1_21 extends Player1_21<ServerPlayer> {
    
    public ServerPlayer1_21(Object player) {
        super(player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}
