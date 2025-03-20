package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.BLOCK;

public class RegisterBlocksEventFabric1_19_2 extends RegisterBlocksEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return BLOCK;
    }
}