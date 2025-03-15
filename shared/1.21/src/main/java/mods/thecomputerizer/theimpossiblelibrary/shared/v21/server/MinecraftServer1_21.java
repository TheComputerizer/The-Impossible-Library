package mods.thecomputerizer.theimpossiblelibrary.shared.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelDirectory;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public abstract class MinecraftServer1_21 extends MinecraftServerAPI<MinecraftServer> {

    @Override public void registerCommand(CommandAPI cmd) {}

    @Override public void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server))
            server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),command);
    }
    
    @SuppressWarnings("SameParameterValue")
    protected @Nullable Field getField(Object parent, String name, Class<?> descType) {
        Class<?> parentClass = parent instanceof MinecraftServer ? MinecraftServer.class : parent.getClass();
        CoreAPI core = CoreAPI.getInstance();
        String clsName = core.mapClassName(parentClass.getName());
        name = core.mapFieldName(clsName,name,"L"+descType.getName()+";");
        return ReflectionHelper.getField(parentClass,name);
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
        String fieldName = CoreAPI.isNamedEnv() ? "levelDirectory" :
                (CoreAPI.isSrgEnv() ? "f_230867_" : "field_23768");
        ClassHelper.checkBurningWaveInit();
        LevelDirectory dir = Fields.getDirect(save,fieldName);
        return dir.path();
    }
    
    protected @Nullable Object getLevelSave(Object server) {
        Field saveField = getLevelSaveField(server);
        return ReflectionHelper.getFieldInstance(server,saveField);
    }
    
    protected abstract @Nullable Field getLevelSaveField(Object server);
    
    @Override public abstract MinecraftServer getServer();
}