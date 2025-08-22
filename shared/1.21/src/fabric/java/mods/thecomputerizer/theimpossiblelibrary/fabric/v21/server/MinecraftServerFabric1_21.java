package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.server;

import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.MinecraftServer1_21;
import net.minecraft.server.MinecraftServer;

public class MinecraftServerFabric1_21 extends MinecraftServer1_21 {
    
    private static final String saveField = NAMED_ENV ? "storageSource" : "field_23784";
    private static final String levelDirectoryField = NAMED_ENV ? "levelDirectory" : "field_23768";
    
    public MinecraftServerFabric1_21() {
        super(saveField,levelDirectoryField);
    }
    
    @Override public MinecraftServer getServer() {
        return (MinecraftServer)FabricHelper.getCurrentServer();
    }
}