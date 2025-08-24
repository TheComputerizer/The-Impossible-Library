package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.LivingDamageEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.entity.Damage1_19;
import net.fabricmc.fabric.api.event.Event;

public class LivingDamageEventFabric1_19 extends LivingDamageEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void setAmount(float amount) {
        this.damage.set(this.event,new Damage1_19(this.event[0],amount));
    }
    
    @Override protected EventFieldWrapper<Object[],DamageAPI> wrapDamageField() {
        return wrapGenericGetter(args -> new Damage1_19(args[0],1f), null);
    }
}