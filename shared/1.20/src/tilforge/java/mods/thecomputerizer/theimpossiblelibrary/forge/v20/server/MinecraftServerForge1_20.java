package mods.thecomputerizer.theimpossiblelibrary.forge.v20.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.MinecraftServer1_20;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MinecraftServerForge1_20 extends MinecraftServer1_20 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "f_129744_";
    private static final String levelDirField = NAMED_ENV ? "levelDirectory" : "f_230867";
    
    public MinecraftServerForge1_20() {
        super(saveField,levelDirField);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}