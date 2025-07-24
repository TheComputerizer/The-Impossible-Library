package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.entity;

import com.mojang.authlib.GameProfile;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Player1_16_5;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.GameType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.world.level.GameType.NOT_SET;

public class ClientPlayer1_16_5 extends Player1_16_5<LocalPlayer> {
    
    public ClientPlayer1_16_5(Object player) {
        super(player);
    }
    
    /**
     * Not available on the client side by default in 1.16+
     */
    @Override public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) {
        return null;
    }
    
    private GameType getGamemode(@Nullable ClientPacketListener handler, GameProfile profile) {
        PlayerInfo info = Objects.nonNull(handler) ? handler.getPlayerInfo(profile.getId()) : null;
        GameType type = Objects.nonNull(info) ? info.getGameMode() : null;
        return Objects.nonNull(type) ? type : NOT_SET;
    }
    
    @Override public int getGamemodeOrdinal() {
        return getGamemode(this.entity.connection,this.entity.getGameProfile()).getId();
    }
    
    @Override public boolean isClientPlayer() {
        return true;
    }
}