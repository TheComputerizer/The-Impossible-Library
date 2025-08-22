package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.MinecraftServer1_18_2;
import net.minecraft.server.MinecraftServer;

public class MinecraftServerFabric1_18_2 extends MinecraftServer1_18_2 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "field_23784";
    private static final String levelPathField = NAMED_ENV ? "levelPath" : "field_23768";
    
    public MinecraftServerFabric1_18_2() {
        super(saveField,levelPathField);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}