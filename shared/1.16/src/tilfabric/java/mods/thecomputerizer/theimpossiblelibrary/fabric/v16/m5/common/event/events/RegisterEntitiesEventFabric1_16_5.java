package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.ENTITY_TYPE;

public class RegisterEntitiesEventFabric1_16_5 extends RegisterEntitiesEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return ENTITY_TYPE;
    }
}