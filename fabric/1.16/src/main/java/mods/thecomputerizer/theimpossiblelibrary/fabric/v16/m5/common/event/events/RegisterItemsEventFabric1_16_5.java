package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterItemsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.RegistryEventFabric1_16_5;
import net.fabricmc.fabric.api.event.Event;

public class RegisterItemsEventFabric1_16_5 extends RegisterItemsEventFabric implements RegistryEventFabric1_16_5 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(ItemAPI<?> entry) {
        register("item",entry);
    }
}
