package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.server;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server.MinecraftServerNeoForge1_20;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class MinecraftServerNeoForge1_20_4 extends MinecraftServerNeoForge1_20 {
    
    public MinecraftServerNeoForge1_20_4() {
        super();
    }
    
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}