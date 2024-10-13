package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterItemsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.RegistryEventFabric1_18_2;
import net.fabricmc.fabric.api.event.Event;

public class RegisterItemsEventFabric1_18_2 extends RegisterItemsEventFabric implements RegistryEventFabric1_18_2 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(ItemAPI<?> entry) {
        register("item",entry);
    }
}