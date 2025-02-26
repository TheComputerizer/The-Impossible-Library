package mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.entity.Player1_19;
import net.minecraft.server.level.ServerPlayer;

public class ServerPlayer1_19 extends Player1_19<ServerPlayer> {
    
    public ServerPlayer1_19(Object player) {
        super(player);
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}
