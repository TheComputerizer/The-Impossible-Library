package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterItemsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.RegistryEventFabric1_19;
import net.fabricmc.fabric.api.event.Event;

public class RegisterItemsEventFabric1_19 extends RegisterItemsEventFabric implements RegistryEventFabric1_19 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(ItemAPI<?> entry) {
        register("item",entry);
    }
}