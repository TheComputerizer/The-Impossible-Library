package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.MinecraftServer1_16_5;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class MinecraftServerFabric1_16_5 extends MinecraftServer1_16_5 {
    
    @Override protected @Nullable Field getLevelPathField(Object save) {
        return FabricHelper.getObfField("field_237279_c_",save.getClass(),Path.class);
    }
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
        return FabricHelper.getObfField("field_71310_m",server.getClass(),LevelStorageAccess.class);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}