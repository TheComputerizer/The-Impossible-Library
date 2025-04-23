package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.MinecraftServer1_21;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class MinecraftServerNeoForge1_21 extends MinecraftServer1_21 {
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
        return getField(server,"storageSource",LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}