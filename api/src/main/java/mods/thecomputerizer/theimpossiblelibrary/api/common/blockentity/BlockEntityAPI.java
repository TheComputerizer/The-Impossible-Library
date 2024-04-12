package mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@Getter
public abstract class BlockEntityAPI<E,T> implements RegistryEntryAPI<T> {

    protected final E entity;
    protected final T type;

    protected BlockEntityAPI(E entity, T type) {
        this.entity = entity;
        this.type = type;
    }

    @Override
    public T getValue() {
        return this.type;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends T> getValueClass() {
        return (Class<? extends T>)this.type.getClass();
    }
}