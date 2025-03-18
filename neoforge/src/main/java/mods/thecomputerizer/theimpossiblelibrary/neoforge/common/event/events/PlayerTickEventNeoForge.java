package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerTickEventWrapper;
import net.neoforged.bus.api.Event;

public abstract class PlayerTickEventNeoForge<E extends Event> extends PlayerTickEventWrapper<E> {
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
    }
}