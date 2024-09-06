package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.BasicWrapped;

import javax.annotation.Nullable;

public abstract class RegistryAPI<V> {

    @Getter protected final Class<V> type;
    @Getter protected final ResourceLocationAPI<?> registryKey;
    protected final Object backend;

    protected RegistryAPI(Object backend, Class<V> type, ResourceLocationAPI<?> registryKey) {
        this.backend = backend;
        this.type = type;
        this.registryKey = registryKey;
    }
    
    public <B> B getBackend() {
        return BasicWrapped.cast(this.backend);
    }

    public abstract ResourceLocationAPI<?> getKey(V value);

    @IndirectCallers
    public @Nullable ResourceLocationAPI<?> getKeyNullable(V value) {
        return hasValue(value) ? getKey(value) : null;
    }

    public abstract V getValue(ResourceLocationAPI<?> key);
    
    @IndirectCallers
    public @Nullable V getValueNullable(ResourceLocationAPI<?> key) {
        return hasKey(key) ? getValue(key) : null;
    }

    public abstract boolean hasKey(ResourceLocationAPI<?> key);
    public abstract boolean hasValue(V value);
}