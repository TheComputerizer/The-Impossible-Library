package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import net.minecraftforge.eventbus.api.Event;

public abstract class EntityEnteringChunkEventForge<E extends Event> extends EntityEnteringChunkEventWrapper<E> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}