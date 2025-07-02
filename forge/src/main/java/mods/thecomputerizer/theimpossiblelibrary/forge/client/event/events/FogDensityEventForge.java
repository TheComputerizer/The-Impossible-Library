package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.ClientForgeEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class FogDensityEventForge<E> extends FogDensityEventWrapper<E>
        implements ClientForgeEvent {
    
    @Override public void cancel() {
        ((Event)this.event).setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(((Event)event).isCanceled());
    }
}