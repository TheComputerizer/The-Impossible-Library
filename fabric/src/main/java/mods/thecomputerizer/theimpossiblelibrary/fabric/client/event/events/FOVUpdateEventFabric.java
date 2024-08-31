package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class FOVUpdateEventFabric extends FOVUpdateEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override
    protected EventFieldWrapper<Object[],Float> wrapFOVField() {
        return wrapGenericGetter(FOVUpdateEvent::getFov,0f);
    }

    @Override
    protected EventFieldWrapper<Object[],Float> wrapNewFOVField() {
        return wrapGenericBoth(FOVUpdateEvent::getNewfov,FOVUpdateEvent::setNewfov,0f);
    }

    @Override
    protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(FOVUpdateEvent::getEntity);
    }
}