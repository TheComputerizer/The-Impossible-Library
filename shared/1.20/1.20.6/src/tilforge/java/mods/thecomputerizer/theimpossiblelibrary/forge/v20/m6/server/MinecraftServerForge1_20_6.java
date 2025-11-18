package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.MinecraftServer1_20;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class MinecraftServerForge1_20_6 extends MinecraftServer1_20 {
    
    public MinecraftServerForge1_20_6() {
        super("storageSource","levelDirectory");
    }
    
    @SuppressWarnings("UnstableApiUsage")
    @Override public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}