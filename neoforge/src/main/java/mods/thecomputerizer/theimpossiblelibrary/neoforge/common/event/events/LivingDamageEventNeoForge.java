package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDamageEventWrapper;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public abstract class LivingDamageEventNeoForge<E extends LivingEvent> extends LivingDamageEventWrapper<E> {
    
    @Override public void cancel() {
        if(this.event instanceof ICancellableEvent cancellable) cancellable.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        if(event instanceof ICancellableEvent cancellable) setCanceled(cancellable.isCanceled());
    }
    
    @Override protected EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingEvent::getEntity);
    }
}