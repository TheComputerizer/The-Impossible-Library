package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseFinishEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class LivingItemUseFinishEventFabric extends LivingItemUseFinishEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],Integer> wrapDurationField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,duration) -> {},0);
    }

    @Override protected EventFieldWrapper<Object[],LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],ItemStackAPI<?>> wrapStackResultField() {
        return wrapItemStackBoth(wrapArrayGetter(0),(args,stack) -> {});
    }

    @Override protected EventFieldWrapper<Object[],ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(wrapArrayGetter(0));
    }
}