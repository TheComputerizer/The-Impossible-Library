package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK;

public class RegisterBlocksEventFabric1_20 extends RegisterBlocksEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return BLOCK;
    }
}