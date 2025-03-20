package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterItemsEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public class RegisterItemsEventFabric1_20 extends RegisterItemsEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return ITEM;
    }
}