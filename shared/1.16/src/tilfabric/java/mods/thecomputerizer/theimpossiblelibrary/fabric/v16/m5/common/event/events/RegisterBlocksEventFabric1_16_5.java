package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.BLOCK;

public class RegisterBlocksEventFabric1_16_5 extends RegisterBlocksEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return BLOCK;
    }
}