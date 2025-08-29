package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.RegisterCommandsEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;

public class RegisterCommandsEventNeoForge1_20_4 extends RegisterCommandsEventNeoForge {
    
    public RegisterCommandsEventNeoForge1_20_4() {
        super(WrappedCommand1_20_4::register);
    }
}