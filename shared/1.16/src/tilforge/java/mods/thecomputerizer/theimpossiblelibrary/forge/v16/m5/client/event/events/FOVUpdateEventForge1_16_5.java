package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVUpdateEventForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEventForge1_16_5 extends FOVUpdateEventForge<FOVUpdateEvent> {
    
    @SubscribeEvent
    public static void onEvent(FOVUpdateEvent event) {
        FOV_UPDATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<FOVUpdateEvent,Float> wrapFOVField() {
        return wrapGenericGetter(getter("getFov"),0f);
    }
    
    @Override protected EventFieldWrapper<FOVUpdateEvent,Float> wrapNewFOVField() {
        return wrapGenericBoth(getter("getNewfov"),setter("setNewfov"),0f);
    }
    
    @Override protected EventFieldWrapper<FOVUpdateEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(FOVUpdateEvent::getEntity);
    }
}
