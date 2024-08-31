package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class PlayerPickupItemEventFabric extends PlayerPickupItemEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(ItemPickupEvent::getOriginalEntity);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemPickupEvent::getPlayer);
    }

    @Override protected EventFieldWrapper<Object[],ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemPickupEvent::getStack);
    }
}