package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.LivingDeathEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity.Damage1_16_5;
import net.fabricmc.fabric.api.event.Event;

public class LivingDeathEventFabric1_16_5 extends LivingDeathEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],DamageAPI> wrapDamageField() {
        return wrapGenericGetter(args -> new Damage1_16_5(args[0],1f), null);
    }
}
