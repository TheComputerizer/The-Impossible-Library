package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface RegistryEntryAPI<V> {

    default RegistryAPI<?> getRegistry() {
        return RegistryHelper.getRegistry(getValueClass());
    }
    ResourceLocationAPI<?> getRegistryName();
    V getValue();
    Class<? extends V> getValueClass();
}