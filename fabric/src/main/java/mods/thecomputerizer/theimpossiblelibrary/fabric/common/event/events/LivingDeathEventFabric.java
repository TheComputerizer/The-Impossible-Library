package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingDeathEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;

public abstract class LivingDeathEventFabric extends LivingDeathEventWrapper<Object[]> implements CommonFabricEvent {

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(LivingDeathEvent::getEntityLiving);
    }
}
