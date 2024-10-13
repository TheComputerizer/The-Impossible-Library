package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public interface RegistryEventFabric1_18_2 {
    
    default <E> void register(String registryName, RegistryEntryAPI<E> entry) {
        register(RegistryHelper.getRegistry(ResourceHelper.getResource(registryName)),entry);
    }
    
    default <E> void register(RegistryAPI<?> registry, RegistryEntryAPI<E> entry) {
        register(registry,entry.getRegistryName(),entry.unwrap());
    }
    
    default <E> void register(RegistryAPI<?> registry, ResourceLocationAPI<?> name, E entry) {
        Registry.register(registry.getBackend(),(ResourceLocation)name.unwrap(),entry);
    }
}