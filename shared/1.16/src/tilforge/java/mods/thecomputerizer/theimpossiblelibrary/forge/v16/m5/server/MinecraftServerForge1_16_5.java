package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.MinecraftServer1_16_5;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class MinecraftServerForge1_16_5 extends MinecraftServer1_16_5 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "field_71310_m";
    private static final String levelPathField = NAMED_ENV ? "levelPath" : "field_237279_c_";
    
    public MinecraftServerForge1_16_5() {
        super(saveField,levelPathField);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}