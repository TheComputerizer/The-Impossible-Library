package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.client.event.FOVUpdateEvent;

public class FOVUpdateEventForge extends FOVUpdateEventWrapper<FOVUpdateEvent> {

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,Float> wrapFOVField() {
        return wrapGenericGetter(FOVUpdateEvent::getFov,0f);
    }

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,Float> wrapNewFOVField() {
        return wrapGenericBoth(FOVUpdateEvent::getNewfov,FOVUpdateEvent::setNewfov,0f);
    }

    @Override
    protected EventFieldWrapper<FOVUpdateEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(FOVUpdateEvent::getEntity);
    }
}