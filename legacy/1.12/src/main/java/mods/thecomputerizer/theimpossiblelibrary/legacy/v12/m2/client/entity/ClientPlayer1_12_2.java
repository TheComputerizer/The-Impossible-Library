package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.entity;

import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Player1_12_2;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;

import javax.annotation.Nullable;
import java.util.Objects;

public class ClientPlayer1_12_2 extends Player1_12_2<EntityPlayerSP> {

    public ClientPlayer1_12_2(EntityPlayerSP player) {
        super(player);
    }

    @Override public int getGamemodeOrdinal() {
        NetworkPlayerInfo info = getNetworkInfo();
        return Objects.nonNull(info) ? info.getGameType().getID() : 0;
    }

    private @Nullable NetworkPlayerInfo getNetworkInfo() {
        return Objects.nonNull(this.entity.connection) ?
                this.entity.connection.getPlayerInfo(this.entity.getGameProfile().getId()) : null;
    }

    @Override public boolean isClientPlayer() {
        return true;
    }
}