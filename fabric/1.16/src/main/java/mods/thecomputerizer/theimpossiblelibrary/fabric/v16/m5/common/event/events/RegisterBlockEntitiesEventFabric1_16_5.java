package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlockEntitiesEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.BLOCK_ENTITY_TYPE;

public class RegisterBlockEntitiesEventFabric1_16_5 extends RegisterBlockEntitiesEventFabric {
    
    @SuppressWarnings("unchecked")
    @Override public <E> Registry<E> getRegistry() {
        return (Registry<E>)BLOCK_ENTITY_TYPE;
    }

}