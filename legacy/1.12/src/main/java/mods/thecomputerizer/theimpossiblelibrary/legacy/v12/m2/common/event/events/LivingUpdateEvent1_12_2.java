package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingUpdateEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_UPDATE;

public class LivingUpdateEvent1_12_2 extends LivingUpdateEventWrapper<LivingUpdateEvent> {

    @SubscribeEvent
    public static void onEvent(LivingUpdateEvent event) {
        LIVING_UPDATE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LivingUpdateEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<LivingUpdateEvent,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingUpdateEvent::getEntityLiving);
    }
}