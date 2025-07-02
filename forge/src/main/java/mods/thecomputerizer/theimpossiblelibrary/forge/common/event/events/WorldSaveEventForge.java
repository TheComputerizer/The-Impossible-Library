package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldSaveEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class WorldSaveEventForge<E extends Event> extends WorldSaveEventWrapper<E>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}