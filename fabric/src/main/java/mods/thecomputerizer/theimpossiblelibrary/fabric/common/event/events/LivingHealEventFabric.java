package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingHealEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class LivingHealEventFabric extends LivingHealEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],Float> wrapAmountField() {
        return wrapGenericBoth(LivingHealEvent::getAmount,LivingHealEvent::setAmount,0f);
    }

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingEvent::getEntityLiving);
    }
}