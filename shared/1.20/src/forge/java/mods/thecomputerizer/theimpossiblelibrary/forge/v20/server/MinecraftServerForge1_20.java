package mods.thecomputerizer.theimpossiblelibrary.forge.v20.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.MinecraftServer1_20;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class MinecraftServerForge1_20 extends MinecraftServer1_20 {
    
    static String saveField = DEV ? "storageSource" : "f_129744_";
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
       return getField(server,saveField,LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}