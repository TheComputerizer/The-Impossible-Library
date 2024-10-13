package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.LootingLevelEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.entity.Damage1_18_2;
import net.fabricmc.fabric.api.event.Event;

public class LootingLevelEventFabric1_18_2 extends LootingLevelEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],DamageAPI> wrapDamageField() {
        return wrapGenericGetter(args -> new Damage1_18_2(args[0],1f), null);
    }
}