package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingUpdateEvent1_16_5 extends LivingUpdateEventWrapper<LivingUpdateEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingUpdateEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<LivingUpdateEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingUpdateEvent::getEntityLiving);
    }
}