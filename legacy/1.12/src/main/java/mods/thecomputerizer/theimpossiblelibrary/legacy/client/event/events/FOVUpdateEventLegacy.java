package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import net.minecraftforge.client.event.FOVUpdateEvent;

public class FOVUpdateEventLegacy extends FOVUpdateEventWrapper<FOVUpdateEvent> {

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,Float> wrapFOVField() {
        return getFieldWrapperGetter(FOVUpdateEvent::getFov,0f);
    }

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,Float> wrapNewFOVField() {
        return getFieldWrapperBoth(FOVUpdateEvent::getNewfov,FOVUpdateEvent::setNewfov,0f);
    }

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,PlayerAPI<?>> wrapPlayerField() {
        return getFieldWrapperGetter(event -> wrapPlayer(FOVUpdateEvent::getEntity),null);
    }
}