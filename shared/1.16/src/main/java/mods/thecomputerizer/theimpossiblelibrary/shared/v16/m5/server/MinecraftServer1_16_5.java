package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class MinecraftServer1_16_5 extends MinecraftServerAPI<MinecraftServer> {

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
        Object server = getServer();
        if(Objects.isNull(server)) {
            TILRef.logError("Unable to get the save directory as the server did not exist! Was this called from "+
                            "the client side?");
            return null;
        }
        Object save = getLevelSave(server);
        if(Objects.isNull(save)) {
            TILRef.logError("Failed to get LevelSave instance from server");
            return null;
        }
        Path path = getLevelPath(save);
        if(Objects.isNull(path)) {
            TILRef.logError("Failed to get path instance from LevelSave");
            return null;
        }
        return path.toFile();
    }
    
    protected @Nullable Path getLevelPath(Object save) {
        Field pathField = getLevelPathField(save);
        Object path = ReflectionHelper.getFieldInstance(save,pathField);
        return path instanceof Path ? (Path)path : null;
    }
    
    protected abstract @Nullable Field getLevelPathField(Object save);
    
    protected @Nullable Object getLevelSave(Object server) {
        Field saveField = getLevelSaveField(server);
        return ReflectionHelper.getFieldInstance(server,saveField);
    }
    
    protected abstract @Nullable Field getLevelSaveField(Object server);
    
    @Override public abstract MinecraftServer getServer();
}