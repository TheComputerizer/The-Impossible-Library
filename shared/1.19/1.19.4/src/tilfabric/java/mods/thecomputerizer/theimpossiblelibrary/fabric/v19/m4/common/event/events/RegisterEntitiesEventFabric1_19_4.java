package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE;

public class RegisterEntitiesEventFabric1_19_4 extends RegisterEntitiesEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return ENTITY_TYPE;
    }
}