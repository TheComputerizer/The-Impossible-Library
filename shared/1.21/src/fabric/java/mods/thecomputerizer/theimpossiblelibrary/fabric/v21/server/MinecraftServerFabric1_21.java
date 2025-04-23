package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.MinecraftServer1_21;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class MinecraftServerFabric1_21 extends MinecraftServer1_21 {
    
    static String saveField = DEV ? "storageSource" : "field_23784";
    
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
        return getField(server,saveField,LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}