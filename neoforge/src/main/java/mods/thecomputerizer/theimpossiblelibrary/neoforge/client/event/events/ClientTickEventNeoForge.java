package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientTickEventWrapper;
import net.neoforged.bus.api.Event;

public abstract class ClientTickEventNeoForge<E extends Event> extends ClientTickEventWrapper<E> {
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
    }
}