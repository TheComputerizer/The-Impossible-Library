package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterCommandsEventWrapper;
import net.minecraftforge.event.RegisterCommandsEvent;

public abstract class RegisterCommandsEventForge extends RegisterCommandsEventWrapper<RegisterCommandsEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RegisterCommandsEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}
