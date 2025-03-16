package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE;

public class RegisterBlockEntitiesEventFabric1_19_4 extends RegisterBlockEntitiesEventFabric {
    
    @SuppressWarnings("unchecked")
    @Override public <E> Registry<E> getRegistry() {
        return (Registry<E>)BLOCK_ENTITY_TYPE;
    }
}
