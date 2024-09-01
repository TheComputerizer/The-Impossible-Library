package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;

public abstract class Registry1_16_5<V> extends RegistryAPI<V> {

    public Registry1_16_5(Object backend, Class<V> type, ResourceLocation1_16_5 registryKey) {
        super(backend,type,registryKey);
    }

    @Override
    public abstract ResourceLocation1_16_5 getKey(V value);
}
