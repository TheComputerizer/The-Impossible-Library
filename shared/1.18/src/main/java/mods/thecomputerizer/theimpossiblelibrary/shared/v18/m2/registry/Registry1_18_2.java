package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public abstract class Registry1_18_2<V> extends RegistryAPI<V> {

    public Registry1_18_2(Object backend, Class<V> type, ResourceLocationAPI<?> registryKey) {
        super(backend,type,registryKey);
    }
}