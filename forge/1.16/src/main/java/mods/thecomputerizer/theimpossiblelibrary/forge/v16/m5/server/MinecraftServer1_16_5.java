package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server;

import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Objects;

public class MinecraftServer1_16_5 extends MinecraftServerAPI<MinecraftServer> {

    @Override
    protected void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server)) server.getCommands().performCommand(server.createCommandSourceStack(),command);
    }

    @Override
    protected MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
