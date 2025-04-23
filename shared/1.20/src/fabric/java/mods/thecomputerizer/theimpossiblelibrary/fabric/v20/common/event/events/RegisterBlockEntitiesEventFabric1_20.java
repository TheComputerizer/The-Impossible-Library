package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE;

public class RegisterBlockEntitiesEventFabric1_20 extends RegisterBlockEntitiesEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return BLOCK_ENTITY_TYPE;
    }
}