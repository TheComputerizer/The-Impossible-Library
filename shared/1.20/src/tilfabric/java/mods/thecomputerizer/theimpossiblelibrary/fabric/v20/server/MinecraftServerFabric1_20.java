package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.MinecraftServer1_20;
import net.minecraft.server.MinecraftServer;

public class MinecraftServerFabric1_20 extends MinecraftServer1_20 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "field_23784";
    private static final String levelDirectoryField = NAMED_ENV ? "levelDirectory" : "field_23768";
    
    public MinecraftServerFabric1_20() {
        super(saveField,levelDirectoryField);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}