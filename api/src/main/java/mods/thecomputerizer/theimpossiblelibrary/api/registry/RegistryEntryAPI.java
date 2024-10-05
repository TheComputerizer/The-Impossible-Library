package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.Wrapped;

public interface RegistryEntryAPI<V> extends Wrapped<V> {

    default RegistryAPI<?> getRegistry() {
        return RegistryHelper.getRegistry(getWrappedClass());
    }
    
    default ResourceLocationAPI<?> getRegistryName() {
        return getRegistry().getKey(unwrap());
    }
}