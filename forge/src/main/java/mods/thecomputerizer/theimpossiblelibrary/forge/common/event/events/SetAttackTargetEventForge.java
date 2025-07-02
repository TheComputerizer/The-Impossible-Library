package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SetAttackTargetEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEvent;
import net.minecraftforge.eventbus.api.Event;

public abstract class SetAttackTargetEventForge<E extends Event> extends SetAttackTargetEventWrapper<E>
        implements CommonForgeEvent {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}