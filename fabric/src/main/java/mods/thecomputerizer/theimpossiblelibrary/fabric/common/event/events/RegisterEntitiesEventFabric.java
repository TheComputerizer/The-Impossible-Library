package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterEntitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.RegistryEventFabric;
import net.fabricmc.fabric.api.event.Event;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.REGISTER_ENTITIES;

public abstract class RegisterEntitiesEventFabric extends RegisterEntitiesEventWrapper<Object[]> implements RegistryEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return REGISTER_ENTITIES;
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        TILRef.logInfo("REGISTERING ENTITY ENTRY");
        registerEntry(entry);
    }
}