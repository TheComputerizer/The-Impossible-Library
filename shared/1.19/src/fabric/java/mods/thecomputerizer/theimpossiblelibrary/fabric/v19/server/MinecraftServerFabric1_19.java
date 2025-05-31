package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.MinecraftServer1_19;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class MinecraftServerFabric1_19 extends MinecraftServer1_19 {
    
    static String saveField = DEV ? "storageSource" : "field_23784";
    
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
        return getField(server,saveField,LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}