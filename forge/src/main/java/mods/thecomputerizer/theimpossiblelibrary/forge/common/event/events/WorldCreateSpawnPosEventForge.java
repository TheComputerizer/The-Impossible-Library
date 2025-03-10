package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldCreateSpawnPosEventWrapper;
import net.minecraftforge.eventbus.api.Event;

public abstract class WorldCreateSpawnPosEventForge<E extends Event> extends WorldCreateSpawnPosEventWrapper<E> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}