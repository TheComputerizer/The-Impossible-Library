package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.RegistryEventFabric1_16_5;
import net.fabricmc.fabric.api.event.Event;

public class RegisterEntitiesEventFabric1_16_5 extends RegisterEntitiesEventFabric implements RegistryEventFabric1_16_5 {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        register("entity",entry);
    }
}
