package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerVisibilityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class PlayerVisibilityEventForge<E extends Event> extends PlayerVisibilityEventWrapper<E>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}