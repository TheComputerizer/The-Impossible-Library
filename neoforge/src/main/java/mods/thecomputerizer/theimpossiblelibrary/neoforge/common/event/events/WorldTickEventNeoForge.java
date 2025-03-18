package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldTickEventWrapper;
import net.neoforged.bus.api.Event;

public abstract class WorldTickEventNeoForge<E extends Event> extends WorldTickEventWrapper<E> {
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
    }
}