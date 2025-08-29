package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.RegisterCommandsEventNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21;

public class RegisterCommandsEventNeoForge1_21 extends RegisterCommandsEventNeoForge {
    
    public RegisterCommandsEventNeoForge1_21() {
        super(WrappedCommand1_21::register);
    }
}