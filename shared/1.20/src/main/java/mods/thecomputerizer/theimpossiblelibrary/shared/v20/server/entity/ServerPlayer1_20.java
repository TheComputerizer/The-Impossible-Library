package mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity.Player1_20;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayer1_20 extends Player1_20<ServerPlayer> {
    
    public ServerPlayer1_20(Object player) {
        super(player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}
