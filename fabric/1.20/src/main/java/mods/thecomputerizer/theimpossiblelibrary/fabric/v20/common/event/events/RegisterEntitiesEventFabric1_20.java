package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.RegistryEventFabric1_20;
import net.fabricmc.fabric.api.event.Event;

public class RegisterEntitiesEventFabric1_20 extends RegisterEntitiesEventFabric implements RegistryEventFabric1_20 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        register("entity",entry);
    }
}