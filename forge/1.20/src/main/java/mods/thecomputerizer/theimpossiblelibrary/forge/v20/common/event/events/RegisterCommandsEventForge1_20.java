package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterCommandsEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.WrappedCommand1_20;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public class RegisterCommandsEventForge1_20 extends RegisterCommandsEventForge {
    
    @SubscribeEvent
    public static void onEvent(RegisterCommandsEvent event) {
        REGISTER_COMMANDS.invoke(event);
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommand1_20.register(getEvent().getDispatcher(),command);
    }
}