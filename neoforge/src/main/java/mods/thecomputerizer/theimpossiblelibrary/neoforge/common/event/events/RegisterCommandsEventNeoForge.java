package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterCommandsEventWrapper;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public abstract class RegisterCommandsEventNeoForge extends RegisterCommandsEventWrapper<RegisterCommandsEvent> {
    
    @Override public void setEvent(RegisterCommandsEvent event) {
        super.setEvent(event);
    }
}
