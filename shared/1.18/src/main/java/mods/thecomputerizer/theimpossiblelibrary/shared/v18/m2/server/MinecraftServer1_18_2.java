package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class MinecraftServer1_18_2 extends MinecraftServerAPI<MinecraftServer> {
    
    private final String saveFieldName;
    private final String levelPathFieldName;
    
    protected MinecraftServer1_18_2(String saveFieldName, String levelPathFieldName) {
        this.saveFieldName = saveFieldName;
        this.levelPathFieldName = levelPathFieldName;
    }

    @Override public void registerCommand(CommandAPI cmd) {}

    @Override public void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server)) server.getCommands().performCommand(server.createCommandSourceStack(),command);
    }
    
    @Override public @Nullable PlayerAPI<?,?> getPlayerByUUID(String uuid) {
        Object player = getServer().getPlayerList().getPlayer(UUID.fromString(uuid));
        return Objects.nonNull(player) ? WrapperHelper.wrapPlayer(player) : null;
    }
    
    @Override public List<PlayerAPI<?,?>> getPlayers() {
        List<PlayerAPI<?,?>> players = new ArrayList<>();
        for(Object player : getServer().getPlayerList().getPlayers())
            players.add(WrapperHelper.wrapPlayer(player));
        return players;
    }
    
    @Override public @Nullable File getSaveDir() {
        MinecraftServer server = getServer();
        if(Objects.isNull(server)) {
            TILRef.logError("Unable to get the save directory as the server did not exist! Was this called from "+
                            "the client side?");
            return null;
        }
        LevelStorageAccess levelAccess = Hacks.getFieldDirect(server,this.saveFieldName);
        if(Objects.isNull(levelAccess)) {
            TILRef.logError("Failed to get LevelSave instance from server");
            return null;
        }
        Path path = Hacks.getFieldDirect(levelAccess,this.levelPathFieldName);
        if(Objects.isNull(path)) {
            TILRef.logError("Failed to get path instance from LevelSave");
            return null;
        }
        return path.toFile();
    }
    
    @Override public abstract MinecraftServer getServer();
}