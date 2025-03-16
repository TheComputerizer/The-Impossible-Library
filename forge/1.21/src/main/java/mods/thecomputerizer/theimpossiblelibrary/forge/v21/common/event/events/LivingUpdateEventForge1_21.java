package mods.thecomputerizer.theimpossiblelibrary.forge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.LivingUpdateEventForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_UPDATE;

public class LivingUpdateEventForge1_21 extends LivingUpdateEventForge<LivingTickEvent> {
    
    @SubscribeEvent
    public static void onEvent(LivingTickEvent event) {
        LIVING_UPDATE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<LivingTickEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingTickEvent::getEntity);
    }
}