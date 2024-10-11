package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.MinecraftServer1_16_5;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormat.LevelSave;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.nio.file.Path;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;

public class MinecraftServerForge1_16_5 extends MinecraftServer1_16_5 {
    
    static String saveField = DEV ? "storageSource" : "field_71310_m";
    static String levelPathField = DEV ? "levelPath" : "field_237279_c_";
    
    @Override protected @Nullable Field getLevelPathField(Object save) {
        return getField(save,levelPathField,Path.class);
    }
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
       return getField(server,saveField,LevelSave.class);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}