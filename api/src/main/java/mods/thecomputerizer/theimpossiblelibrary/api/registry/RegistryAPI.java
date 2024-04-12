package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import javax.annotation.Nullable;

@Getter
public abstract class RegistryAPI<V> {

    protected final Class<V> type;
    protected final ResourceLocationAPI<?> registryKey;

    protected RegistryAPI(Class<V> type, ResourceLocationAPI<?> registryKey) {
        this.type = type;
        this.registryKey = registryKey;
    }

    public abstract ResourceLocationAPI<?> getKey(V value);

    public @Nullable ResourceLocationAPI<?> getKeyNullable(V value) {
        return hasValue(value) ? getKey(value) : null;
    }

    public abstract V getValue(ResourceLocationAPI<?> key);

    public @Nullable V getValueNullable(ResourceLocationAPI<?> key) {
        return hasKey(key) ? getValue(key) : null;
    }

    public abstract boolean hasKey(ResourceLocationAPI<?> key);
    public abstract boolean hasValue(V value);
}
