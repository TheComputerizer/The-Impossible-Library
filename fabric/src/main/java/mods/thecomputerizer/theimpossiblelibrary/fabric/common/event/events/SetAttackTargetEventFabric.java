package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.SetAttackTargetEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class SetAttackTargetEventFabric extends SetAttackTargetEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getEntityLiving);
    }

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapTargetField() {
        return wrapLivingGetter(LivingSetAttackTargetEvent::getTarget);
    }
}