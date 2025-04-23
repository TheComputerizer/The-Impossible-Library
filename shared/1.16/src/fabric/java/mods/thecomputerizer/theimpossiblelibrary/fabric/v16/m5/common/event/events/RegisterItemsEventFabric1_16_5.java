package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterItemsEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.Registry.ITEM;

public class RegisterItemsEventFabric1_16_5 extends RegisterItemsEventFabric {
    
    @Override public Registry<?> getRegistry() {
        return ITEM;
    }
}