package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public abstract class LivingDeathEventNeoForge extends LivingDeathEventWrapper<LivingDeathEvent> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingDeathEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LivingDeathEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingDeathEvent::getEntity);
    }
}