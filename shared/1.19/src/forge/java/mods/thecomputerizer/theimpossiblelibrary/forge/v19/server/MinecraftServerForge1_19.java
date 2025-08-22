package mods.thecomputerizer.theimpossiblelibrary.forge.v19.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v19.server.MinecraftServer1_19;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MinecraftServerForge1_19 extends MinecraftServer1_19 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "f_129744_";
    private static final String levelDirField = NAMED_ENV ? "levelDirectory" : "f_230867";
    
    public MinecraftServerForge1_19() {
        super(saveField,levelDirField);
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}