package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterItemsEventFabric;
import net.minecraft.core.Registry;

import static net.minecraft.core.registries.BuiltInRegistries.ITEM;

public class RegisterItemsEventFabric1_19_4 extends RegisterItemsEventFabric {
    
    @SuppressWarnings("unchecked")
    @Override public <E> Registry<E> getRegistry() {
        return (Registry<E>)ITEM;
    }
}