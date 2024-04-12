package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Player1_16_5;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ServerPlayer1_16_5 extends Player1_16_5<ServerPlayerEntity> {

    public ServerPlayer1_16_5(ServerPlayerEntity player) {
        super(player);
    }

    @Override
    public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }

    @Override
    public boolean isClientPlayer() {
        return false;
    }
}
