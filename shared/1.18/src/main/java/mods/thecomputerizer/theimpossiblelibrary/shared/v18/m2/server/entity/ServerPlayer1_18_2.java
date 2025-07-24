package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.entity.Player1_18_2;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ServerPlayer1_18_2 extends Player1_18_2<ServerPlayer> {
    
    public ServerPlayer1_18_2(Object player) {
        super(player);
    }
    
    /**
     * Returns null if the dimension fails to match correctly or if the respawn position does not exist
     */
    @Override public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) {
        if(Objects.isNull(this.entity)) return null;
        ResourceKey<Level> respawnKey = this.entity.getRespawnDimension();
        ResourceLocationAPI<?> dimensionName = dimension.getRegistryName();
        if(Objects.isNull(dimensionName) || !respawnKey.location().equals(dimensionName.unwrap())) return null;
        BlockPos pos = this.entity.getRespawnPosition();
        return Objects.nonNull(pos) ? WrapperHelper.wrapPosition(pos) : null;
    }
    
    @Override public int getGamemodeOrdinal() {
        return this.entity.gameMode.getGameModeForPlayer().getId();
    }
    
    @Override public boolean isClientPlayer() {
        return false;
    }
}
