package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events;

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
    
    public RegisterCommandsEventNeoForge1_20_4() {
        super(WrappedCommand1_20_4::register);
    }
}