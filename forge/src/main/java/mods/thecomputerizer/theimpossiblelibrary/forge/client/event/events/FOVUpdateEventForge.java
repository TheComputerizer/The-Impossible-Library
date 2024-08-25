package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FOVUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEventForge extends FOVUpdateEventWrapper<FOVUpdateEvent> {
    
    @SubscribeEvent
    public static void onEvent(FOVUpdateEvent event) {
        FOV_UPDATE.invoke(event);
    }
    
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