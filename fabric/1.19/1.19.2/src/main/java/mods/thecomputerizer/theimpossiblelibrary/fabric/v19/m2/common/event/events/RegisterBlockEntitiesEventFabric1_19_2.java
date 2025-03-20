package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.BLOCK_ENTITY_TYPE;

public class RegisterBlockEntitiesEventFabric1_19_2 extends RegisterBlockEntitiesEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return BLOCK_ENTITY_TYPE;
    }
}