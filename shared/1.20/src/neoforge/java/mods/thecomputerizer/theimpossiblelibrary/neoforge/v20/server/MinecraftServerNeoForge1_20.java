package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.MinecraftServer1_20;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public abstract class MinecraftServerNeoForge1_20 extends MinecraftServer1_20 {
    
    static String saveField = CoreAPI.isNamedEnv() ? "storageSource" : "f_129744_";
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
        MinecraftServer server1;
       return getField(server,saveField,LevelStorageAccess.class);
    }
}