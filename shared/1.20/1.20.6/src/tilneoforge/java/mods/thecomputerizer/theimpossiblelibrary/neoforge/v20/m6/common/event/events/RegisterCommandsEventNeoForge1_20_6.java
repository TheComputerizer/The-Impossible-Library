package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.RegisterCommandsEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.server.WrappedCommand1_20_6;

public class RegisterCommandsEventNeoForge1_20_6 extends RegisterCommandsEventNeoForge {
    
    public RegisterCommandsEventNeoForge1_20_6() {
        super(WrappedCommand1_20_6::register);
    }
}