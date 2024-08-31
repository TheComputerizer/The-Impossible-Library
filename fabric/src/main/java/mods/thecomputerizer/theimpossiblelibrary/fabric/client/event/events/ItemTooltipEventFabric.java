package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.ItemTooltipEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ItemTooltipEventFabric extends ItemTooltipEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override
    protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemTooltipEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<Object[],ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemTooltipEvent::getItemStack);
    }

    @Override
    protected EventFieldWrapper<Object[],List<String>> wrapTooltipField() { //TODO Adjust for text components
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }
}