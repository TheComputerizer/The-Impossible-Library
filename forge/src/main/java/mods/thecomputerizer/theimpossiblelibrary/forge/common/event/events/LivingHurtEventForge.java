package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHurtEventWrapper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class LivingHurtEventForge extends LivingHurtEventWrapper<LivingHurtEvent> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingHurtEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LivingHurtEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingHurtEvent::getEntityLiving);
    }
}