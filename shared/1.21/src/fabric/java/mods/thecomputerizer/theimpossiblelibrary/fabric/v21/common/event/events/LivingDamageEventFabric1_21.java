package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.LivingDamageEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.entity.Damage1_21;
import net.fabricmc.fabric.api.event.Event;

public class LivingDamageEventFabric1_21 extends LivingDamageEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void setAmount(float amount) {
        this.damage.set(this.event,new Damage1_21(this.event[0],amount));
    }
    
    @Override protected EventFieldWrapper<Object[],DamageAPI> wrapDamageField() {
        return wrapGenericGetter(args -> new Damage1_21(args[0],1f), null);
    }
}