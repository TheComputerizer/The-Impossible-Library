package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.entity;

import com.mojang.authlib.GameProfile;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.entity.Player1_18_2;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameType;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.world.level.GameType.SURVIVAL;

public class ClientPlayer1_18_2 extends Player1_18_2<LocalPlayer> {
    
    public ClientPlayer1_18_2(Object player) {
        super(player);
    }
    
    private GameType getGamemode(@Nullable ClientPacketListener handler, GameProfile profile) {
        PlayerInfo info = Objects.nonNull(handler) ? handler.getPlayerInfo(profile.getId()) : null;
        GameType type = Objects.nonNull(info) ? info.getGameMode() : null;
        return Objects.nonNull(type) ? type : SURVIVAL;
    }
    
    @Override public int getGamemodeOrdinal() {
        return getGamemode(this.entity.connection,this.entity.getGameProfile()).getId();
    }
    
    @Override public boolean isClientPlayer() {
        return true;
    }
}