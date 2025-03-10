package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingUpdateEventForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_UPDATE;

public class LivingUpdateEventForge1_16_5 extends LivingUpdateEventForge<LivingUpdateEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingUpdateEvent event) {
        LIVING_UPDATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingUpdateEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingUpdateEvent::getEntity);
    }
}