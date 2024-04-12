package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.entity;

import com.mojang.authlib.GameProfile;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Player1_16_5;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.world.GameType;

import javax.annotation.Nullable;
import java.util.Objects;

public class ClientPlayer1_16_5 extends Player1_16_5<ClientPlayerEntity> {

    public ClientPlayer1_16_5(ClientPlayerEntity player) {
        super(player);
    }

    private GameType getGamemode(@Nullable ClientPlayNetHandler handler, GameProfile profile) {
        NetworkPlayerInfo info = Objects.nonNull(handler) ? handler.getPlayerInfo(profile.getId()) : null;
        GameType type = Objects.nonNull(info) ? info.getGameMode() : null;
        return Objects.nonNull(type) ? type : GameType.NOT_SET;
    }

    @Override
    public int getGamemodeOrdinal() {
        return getGamemode(this.entity.connection,this.entity.getGameProfile()).getId();
    }

    @Override
    public boolean isClientPlayer() {
        return true;
    }
}
