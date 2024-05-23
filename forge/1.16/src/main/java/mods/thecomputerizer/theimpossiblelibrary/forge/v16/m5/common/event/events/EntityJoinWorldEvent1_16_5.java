package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityJoinWorldEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityJoinWorldEvent1_16_5 extends EntityJoinWorldEventWrapper<EntityJoinWorldEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityJoinWorldEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<EntityJoinWorldEvent,WorldAPI<?>> wrapWorld() {
        return wrapWorldGetter(EntityJoinWorldEvent::getWorld);
    }

    @Override
    protected EventFieldWrapper<EntityJoinWorldEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityJoinWorldEvent::getEntity);
    }
}