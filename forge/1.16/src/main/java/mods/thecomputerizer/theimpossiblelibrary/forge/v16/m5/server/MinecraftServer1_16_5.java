package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.entity.ServerPlayer1_16_5;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormat.LevelSave;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MinecraftServer1_16_5 extends MinecraftServerAPI<MinecraftServer> {

    @Override
    public void registerCommand(CommandAPI cmd) {}

    @Override
    public void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server)) server.getCommands().performCommand(server.createCommandSourceStack(),command);
    }
    
    @Nullable @Override
    public PlayerAPI<?,?> getPlayerByUUID(String uuid) {
        ServerPlayerEntity player = getServer().getPlayerList().getPlayer(UUID.fromString(uuid));
        return Objects.nonNull(player) ? new ServerPlayer1_16_5(player) : null;
    }
    
    @Override public List<PlayerAPI<?,?>> getPlayers() {
        List<PlayerAPI<?,?>> players = new ArrayList<>();
        for(ServerPlayerEntity player : getServer().getPlayerList().getPlayers())
            players.add(new ServerPlayer1_16_5(player));
        return players;
    }
    
    @Override public @Nullable File getSaveDir() {
        MinecraftServer server = getServer();
        if(Objects.isNull(server)) {
            TILRef.logError("Unable to get the save directory as the server did not exist! Was this called from "+
                            "the client side?");
            return null;
        }
        LevelSave save = getLevelSave(getServer());
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
    
    protected @Nullable Path getLevelPath(LevelSave save) {
        Field pathField = ObfuscationReflectionHelper.findField(LevelSave.class,"field_237279_c_");
        Object path = ReflectionHelper.getFieldInstance(save,pathField);
        return path instanceof Path ? (Path)path : null;
    }
    
    protected @Nullable LevelSave getLevelSave(MinecraftServer server) {
        Field saveField = ObfuscationReflectionHelper.findField(MinecraftServer.class,"field_71310_m");
        Object save = ReflectionHelper.getFieldInstance(server,saveField);
        return save instanceof LevelSave ? (LevelSave)save : null;
    }
    
    @Override
    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
