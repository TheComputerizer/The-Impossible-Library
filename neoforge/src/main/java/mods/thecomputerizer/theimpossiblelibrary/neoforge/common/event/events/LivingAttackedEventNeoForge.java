package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingAttackedEventWrapper;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;

public abstract class LivingAttackedEventNeoForge extends LivingAttackedEventWrapper<LivingAttackEvent> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingAttackEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<LivingAttackEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingAttackEvent::getEntity);
    }
}