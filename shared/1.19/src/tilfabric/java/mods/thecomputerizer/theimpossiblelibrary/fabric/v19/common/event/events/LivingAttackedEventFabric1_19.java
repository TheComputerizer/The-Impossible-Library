package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.LivingAttackedEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.entity.Damage1_19;
import net.fabricmc.fabric.api.event.Event;

public class LivingAttackedEventFabric1_19 extends LivingAttackedEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],DamageAPI> wrapDamageField() {
        return wrapGenericGetter(args -> new Damage1_19(args[0],1f), null);
    }
}
