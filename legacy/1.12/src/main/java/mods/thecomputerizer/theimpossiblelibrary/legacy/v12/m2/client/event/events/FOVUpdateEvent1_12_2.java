package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEvent1_12_2 extends FOVUpdateEventWrapper<FOVUpdateEvent> {

    @SubscribeEvent
    public static void onEvent(FOVUpdateEvent event) {
        FOV_UPDATE.invoke(event);
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
    protected EventFieldWrapper<FOVUpdateEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(FOVUpdateEvent::getEntity);
    }
}