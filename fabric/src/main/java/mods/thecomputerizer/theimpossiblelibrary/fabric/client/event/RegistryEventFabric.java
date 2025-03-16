package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public interface RegistryEventFabric extends CommonFabricEvent {
    
    <E> Registry<E> getRegistry();
    
    default <E> void registerEntry(RegistryEntryAPI<E> entry) {
        registerEntry(getRegistry(),entry.getRegistryName(),entry.unwrap());
    }
    
    default <E> void registerEntry(ResourceLocationAPI<?> name, E entry) {
        registerEntry(getRegistry(),name,entry);
    }
    
    default <E> void registerEntry(Registry<E> registry, RegistryEntryAPI<E> entry) {
        registerEntry(registry,entry.getRegistryName(),entry.unwrap());
    }
    
    default <E> void registerEntry(Registry<E> registry, ResourceLocationAPI<?> name, E entry) {
        Registry.register(registry,(ResourceLocation)name.unwrap(),entry);
    }
}