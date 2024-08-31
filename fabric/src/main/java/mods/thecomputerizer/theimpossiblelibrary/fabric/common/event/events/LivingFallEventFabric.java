package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingFallEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class LivingFallEventFabric extends LivingFallEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],Float> wrapDamageMultiplierField() {
        return wrapGenericBoth(LivingFallEvent::getDamageMultiplier,LivingFallEvent::setDamageMultiplier,0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapDistanceField() {
        return wrapGenericBoth(LivingFallEvent::getDistance,LivingFallEvent::setDistance,0f);
    }

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingFallEvent::getEntityLiving);
    }
}
