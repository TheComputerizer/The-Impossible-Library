package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface RegistryHandlerAPI<R extends RegistryAPI<?>> {

    RegistryEntryAPI<?> getEntryIfPresent(ResourceLocationAPI<?> registryKey, ResourceLocationAPI<?> entryKey);
    R getBiomeRegistry();
    R getBlockRegistry();
    R getBlockEntityRegistry();
    R getEntityRegistry();
    R getItemRegistry();
    R getRegistry(ResourceLocationAPI<?> registryKey);
    R getSoundRegistry();
}
