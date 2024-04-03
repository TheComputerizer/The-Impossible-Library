package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.server;

import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class MinecraftServerForge implements MinecraftServerAPI<MinecraftServer> {

    @Override
    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
