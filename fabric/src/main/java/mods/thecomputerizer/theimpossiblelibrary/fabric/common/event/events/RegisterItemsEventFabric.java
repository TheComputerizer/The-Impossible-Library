package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.RegistryEventFabric;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomComonFabricEvents.REGISTER_ITEMS;

public abstract class RegisterItemsEventFabric extends RegisterItemsEventWrapper<Object[]> implements RegistryEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return REGISTER_ITEMS;
    }
    
    @Override public void register(ItemAPI<?> entry) {
        TILRef.logInfo("REGISTERING ITEM ENTRY");
        registerEntry(entry);
    }
}