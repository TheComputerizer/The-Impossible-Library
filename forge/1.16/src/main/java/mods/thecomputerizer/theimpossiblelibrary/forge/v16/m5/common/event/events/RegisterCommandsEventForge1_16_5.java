package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterCommandsEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.WrappedCommandForge1_16_5;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public class RegisterCommandsEventForge1_16_5 extends RegisterCommandsEventForge {
    
    @SubscribeEvent
    public static void onEvent(RegisterCommandsEvent event) {
        REGISTER_COMMANDS.invoke(event);
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommandForge1_16_5.register(getEvent().getDispatcher(), command);
    }
}