package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v19.common.event.RegistryEventFabric1_19;
import net.fabricmc.fabric.api.event.Event;

public class RegisterEntitiesEventFabric1_19 extends RegisterEntitiesEventFabric implements RegistryEventFabric1_19 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        register("entity",entry);
    }
}