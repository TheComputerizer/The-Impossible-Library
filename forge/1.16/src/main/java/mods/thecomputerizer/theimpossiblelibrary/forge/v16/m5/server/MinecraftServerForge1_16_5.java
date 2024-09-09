package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.MinecraftServer1_16_5;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class MinecraftServerForge1_16_5 extends MinecraftServer1_16_5 {
    
    @SuppressWarnings("unchecked")
    <T> @Nullable Field getField(Class<?> cls, String name) {
        return ObfuscationReflectionHelper.findField((Class<? super T>)cls,name);
    }
    
    @Override protected @Nullable Field getLevelPathField(Object save) {
        return getField(save.getClass(),"field_237279_c_");
    }
    
    @Override protected @Nullable Field getLevelSaveField(Object server) {
       return getField(server.getClass(),"field_71310_m");
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}