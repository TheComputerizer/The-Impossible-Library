package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.ENTITY_TYPE;

public class RegisterEntitiesEventFabric1_19_2 extends RegisterEntitiesEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return ENTITY_TYPE;
    }
}