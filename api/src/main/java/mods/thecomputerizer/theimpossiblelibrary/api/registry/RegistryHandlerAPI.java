package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface RegistryHandlerAPI<R extends RegistryAPI<?>> {

    RegistryEntryAPI<?> getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey);
    R getRegistry(ResourceLocationAPI<?> registryKey);
    R getBiomeRegistry();
    R getBlockRegistry();
    R getBlockEntityRegistry();
    R getEntityRegistry();
    R getItemRegistry();
}
