package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.MinecraftServer1_18_2;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.nio.file.Path;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class MinecraftServerForge1_18_2 extends MinecraftServer1_18_2 {
    
    static String saveField = DEV ? "storageSource" : "f_129744_";
    static String levelPathField = DEV ? "levelPath" : "f_78271_";
    
    @Override protected @Nullable Field getLevelPathField(Object save) {
        return getField(save,levelPathField,Path.class);
    }
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
       return getField(server,saveField,LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}