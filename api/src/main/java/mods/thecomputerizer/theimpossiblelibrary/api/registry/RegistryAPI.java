package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;

public interface RegistryAPI<E extends RegistryEntryAPI<?>> {

    ResourceLocationAPI<?> getKey(E entry);
    @Nullable ResourceLocationAPI<?> getKeyNullable(E entry);
    ResourceLocationAPI<?> getRegistryKey();
    RegistryEntryAPI<E> getValue(ResourceLocationAPI<?> id);
    @Nullable RegistryEntryAPI<E> getValueNullable(ResourceLocationAPI<?> id);
    boolean hasKey(ResourceLocationAPI<?> key);
    boolean hasValue(E entry);
}
