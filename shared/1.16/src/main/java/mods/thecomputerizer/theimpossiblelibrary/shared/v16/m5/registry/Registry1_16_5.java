package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public abstract class Registry1_16_5<V> extends RegistryAPI<V> {

    public Registry1_16_5(Object backend, Class<V> type, ResourceLocationAPI<?> registryKey) {
        super(backend,type,registryKey);
    }
}