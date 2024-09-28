package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Player1_12_2;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerInteractionManager;

import java.util.Objects;

public class ServerPlayer1_12_2 extends Player1_12_2<EntityPlayerMP> {

    public ServerPlayer1_12_2(EntityPlayerMP player) {
        super(player);
    }

    @Override public int getGamemodeOrdinal() {
        PlayerInteractionManager manager = this.entity.interactionManager;
        return Objects.nonNull(manager) ? manager.getGameType().getID() : 0;
    }

    @Override public boolean isClientPlayer() {
        return false;
    }
}