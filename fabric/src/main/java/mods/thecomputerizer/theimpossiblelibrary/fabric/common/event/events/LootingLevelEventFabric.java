package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LootingLevelEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;

public abstract class LootingLevelEventFabric extends LootingLevelEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override protected EventFieldWrapper<Object[],Integer> wrapLootingLevelField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,level) -> {},1);
    }

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(wrapArrayGetter(0));
    }
}