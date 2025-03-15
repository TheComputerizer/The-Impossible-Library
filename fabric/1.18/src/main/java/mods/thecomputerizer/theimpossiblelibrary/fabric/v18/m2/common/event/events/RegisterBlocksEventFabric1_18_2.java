package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.BLOCK;

public class RegisterBlocksEventFabric1_18_2 extends RegisterBlocksEventFabric {
    
    @SuppressWarnings("unchecked")
    @Override public <E> Registry<E> getRegistry() {
        return (Registry<E>)BLOCK;
    }

}