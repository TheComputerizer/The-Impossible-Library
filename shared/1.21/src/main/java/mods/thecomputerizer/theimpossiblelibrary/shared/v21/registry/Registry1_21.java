package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public abstract class Registry1_21<V> extends RegistryAPI<V> {

    public Registry1_21(Object backend, Class<V> type, ResourceLocationAPI<?> registryKey) {
        super(backend,type,registryKey);
    }
}