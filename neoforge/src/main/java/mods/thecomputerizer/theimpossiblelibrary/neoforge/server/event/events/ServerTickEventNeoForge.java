package mods.thecomputerizer.theimpossiblelibrary.neoforge.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;
import net.neoforged.bus.api.Event;

public abstract class ServerTickEventNeoForge<E extends Event> extends ServerTickEventWrapper<E> {
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
    }
}
