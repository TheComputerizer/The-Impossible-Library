package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.TrampleFarmlandEventWrapper;
import net.minecraftforge.eventbus.api.Event;

public abstract class TrampleFarmlandEventForge<E extends Event> extends TrampleFarmlandEventWrapper<E> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}