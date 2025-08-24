package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;

public class RegisterEntitiesEventFabric1_20 extends RegisterEntitiesEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return ENTITY_TYPE;
    }
}