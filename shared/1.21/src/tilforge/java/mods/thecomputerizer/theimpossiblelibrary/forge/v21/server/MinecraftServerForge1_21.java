package mods.thecomputerizer.theimpossiblelibrary.forge.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.MinecraftServer1_21;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MinecraftServerForge1_21 extends MinecraftServer1_21 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "f_129744_";
    private static final String levelDirField = NAMED_ENV ? "levelDirectory" : "f_230867_";
    
    public MinecraftServerForge1_21() {
        super(saveField,levelDirField);
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}