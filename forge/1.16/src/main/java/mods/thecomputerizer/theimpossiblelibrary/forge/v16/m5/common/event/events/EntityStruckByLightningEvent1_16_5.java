package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityStruckByLightningEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

public class EntityStruckByLightningEvent1_16_5 extends EntityStruckByLightningEventWrapper<EntityStruckByLightningEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityStruckByLightningEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<EntityStruckByLightningEvent,EntityAPI<?,?>> wrapLightningEntityField() {
        return wrapEntityGetter(EntityStruckByLightningEvent::getLightning);
    }
}