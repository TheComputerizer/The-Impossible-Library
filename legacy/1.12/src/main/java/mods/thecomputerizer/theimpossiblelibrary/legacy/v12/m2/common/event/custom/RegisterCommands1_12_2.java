package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.custom;

import lombok.Getter;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.Event;

@Getter
public class RegisterCommands1_12_2 extends Event {

    private final MinecraftServer server;

    public RegisterCommands1_12_2(MinecraftServer server) {
        this.server = server;
    }

    public void registerCommand(ICommand command) {
        ((CommandHandler)this.server.commandManager).registerCommand(command);
    }
}