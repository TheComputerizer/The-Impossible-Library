package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public abstract class Registry1_19_4<V> extends RegistryAPI<V> {

    public Registry1_19_4(Object backend, Class<V> type, ResourceLocationAPI<?> registryKey) {
        super(backend,type,registryKey);
    }
}