package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.server.MinecraftServerAPI;
import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Objects;

public class MinecraftServer1_12_2 extends MinecraftServerAPI<MinecraftServer> {

    @Override
    protected void registerCommand(CommandAPI cmd) {
        ((CommandHandler)getServer().commandManager).registerCommand(new WrappedCommand1_12_2(cmd));
    }

    @Override
    protected void executeCommandLiteral(String command) {
        MinecraftServer server = getServer();
        if(Objects.nonNull(server)) server.commandManager.executeCommand(server,command);
    }

    @Override
    protected MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }
}