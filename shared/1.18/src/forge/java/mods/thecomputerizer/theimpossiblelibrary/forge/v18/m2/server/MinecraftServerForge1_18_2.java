package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.MinecraftServer1_18_2;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MinecraftServerForge1_18_2 extends MinecraftServer1_18_2 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "f_129744_";
    private static final String levelPathField = NAMED_ENV ? "levelPath" : "f_78271_";
    
    public MinecraftServerForge1_18_2() {
        super(saveField,levelPathField);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}