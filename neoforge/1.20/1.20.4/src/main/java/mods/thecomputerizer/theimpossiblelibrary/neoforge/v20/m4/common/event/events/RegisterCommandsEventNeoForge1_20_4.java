package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.RegisterCommandsEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public class RegisterCommandsEventNeoForge1_20_4 extends RegisterCommandsEventNeoForge {
    
    @SubscribeEvent
    public static void onEvent(RegisterCommandsEvent event) {
        REGISTER_COMMANDS.invoke(event);
    }
    
    @Override public void setEvent(RegisterCommandsEvent event) {
        super.setEvent(event);
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommand1_20_4.register(getEvent().getDispatcher(),command);
    }
}