package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.MinecraftServer1_16_5;
import net.minecraft.server.MinecraftServer;

public class MinecraftServerFabric1_16_5 extends MinecraftServer1_16_5 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "field_23784";
    private static final String levelPathField = NAMED_ENV ? "levelPath" : "field_23768";
    
    public MinecraftServerFabric1_16_5() {
        super(saveField,levelPathField);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}