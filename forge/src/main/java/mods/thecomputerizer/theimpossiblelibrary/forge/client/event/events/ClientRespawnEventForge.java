package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ClientRespawnEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.ClientForgeEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class ClientRespawnEventForge<E extends Event> extends ClientRespawnEventWrapper<E>
        implements ClientForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}