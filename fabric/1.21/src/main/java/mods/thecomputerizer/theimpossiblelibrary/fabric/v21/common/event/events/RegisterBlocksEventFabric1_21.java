package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterBlocksEventFabric;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK;

public class RegisterBlocksEventFabric1_21 extends RegisterBlocksEventFabric {
    
    @SuppressWarnings("unchecked")
    @Override public <E> Registry<E> getRegistry() {
        return (Registry<E>)BLOCK;
    }
    
    @Override public  <E> void registerEntry(Registry<E> registry, ResourceLocationAPI<?> name, E entry) {
        Registry.register(registry,(ResourceLocation)name.unwrap(),entry);
    }
}