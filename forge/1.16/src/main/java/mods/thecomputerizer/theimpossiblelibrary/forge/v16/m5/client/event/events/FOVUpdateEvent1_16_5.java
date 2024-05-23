package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.client.event.FOVUpdateEvent;

public class FOVUpdateEvent1_16_5 extends FOVUpdateEventWrapper<FOVUpdateEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(FOVUpdateEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<FOVUpdateEvent,Float> wrapFOVField() {
        return wrapGenericGetter(FOVUpdateEvent::getFov,0f);
    }

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,Float> wrapNewFOVField() {
        return wrapGenericBoth(FOVUpdateEvent::getNewfov,FOVUpdateEvent::setNewfov,0f);
    }

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(FOVUpdateEvent::getEntity);
    }
}