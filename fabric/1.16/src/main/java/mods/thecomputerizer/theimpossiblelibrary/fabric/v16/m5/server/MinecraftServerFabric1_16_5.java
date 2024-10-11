package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.MinecraftServer1_16_5;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.nio.file.Path;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class MinecraftServerFabric1_16_5 extends MinecraftServer1_16_5 {
    
    static String saveField = DEV ? "storageSource" : "field_23784";
    static String levelPathField = DEV ? "levelPath" : "field_23768";
    
    @Override protected @Nullable Field getLevelPathField(Object save) {
        return getField(save,levelPathField,Path.class);
    }
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
        return getField(server,saveField,LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}