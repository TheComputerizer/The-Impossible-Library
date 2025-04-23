package mods.thecomputerizer.theimpossiblelibrary.forge.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.MinecraftServer1_21;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class MinecraftServerForge1_21 extends MinecraftServer1_21 {
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
       return getField(server,"storageSource",LevelStorageAccess.class);
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}