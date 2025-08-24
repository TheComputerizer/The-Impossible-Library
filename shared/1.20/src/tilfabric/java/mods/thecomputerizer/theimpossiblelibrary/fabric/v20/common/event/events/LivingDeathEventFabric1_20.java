package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.LivingDeathEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity.Damage1_20;
import net.fabricmc.fabric.api.event.Event;

public class LivingDeathEventFabric1_20 extends LivingDeathEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],DamageAPI> wrapDamageField() {
        return wrapGenericGetter(args -> new Damage1_20(args[0],1f), null);
    }
}
