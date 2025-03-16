package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;

public class RegisterEntitiesEventFabric1_20 extends RegisterEntitiesEventFabric {
    
    @SuppressWarnings("unchecked")
    @Override public <E> Registry<E> getRegistry() {
        return (Registry<E>)ENTITY_TYPE;
    }
    
}