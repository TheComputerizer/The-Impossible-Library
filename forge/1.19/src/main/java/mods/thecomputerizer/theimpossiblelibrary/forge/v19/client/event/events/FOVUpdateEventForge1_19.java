package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.FOVUpdateEventForge;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public class FOVUpdateEventForge1_19 extends FOVUpdateEventForge<FOVModifierEvent> {
    
    @SubscribeEvent
    public static void onEvent(FOVModifierEvent event) {
        FOV_UPDATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<FOVModifierEvent,Float> wrapFOVField() {
        return wrapGenericGetter(FOVModifierEvent::getFov,0f);
    }
    
    @Override protected EventFieldWrapper<FOVModifierEvent,Float> wrapNewFOVField() {
        return wrapGenericBoth(FOVModifierEvent::getNewfov,FOVModifierEvent::setNewfov,0f);
    }
    
    @Override protected EventFieldWrapper<FOVModifierEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(FOVModifierEvent::getEntity);
    }
}
