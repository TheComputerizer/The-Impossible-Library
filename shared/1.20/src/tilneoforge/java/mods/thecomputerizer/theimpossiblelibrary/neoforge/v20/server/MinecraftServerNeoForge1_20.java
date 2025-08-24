package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.server;

import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.MinecraftServer1_20;

public abstract class MinecraftServerNeoForge1_20 extends MinecraftServer1_20 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "f_129744_";
    private static final String levelDirField = NAMED_ENV ? "levelDirectory" : "f_230867";
    
    protected MinecraftServerNeoForge1_20() {
        super(saveField,levelDirField);
    }
}