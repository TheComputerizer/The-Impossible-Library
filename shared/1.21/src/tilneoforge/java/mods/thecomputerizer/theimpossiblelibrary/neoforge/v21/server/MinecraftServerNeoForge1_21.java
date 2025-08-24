package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.MinecraftServer1_21;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class MinecraftServerNeoForge1_21 extends MinecraftServer1_21 {
    
    public MinecraftServerNeoForge1_21() {
        super("storageSource","levelDirectory");
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}