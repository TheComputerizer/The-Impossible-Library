package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@Getter
public abstract class EffectAPI<E> implements RegistryEntryAPI<E> {

    protected final E effect;

    protected EffectAPI(E effect) {
        this.effect = effect;
    }

    @Override
    public E getValue() {
        return this.effect;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends E> getValueClass() {
        return (Class<? extends E>)this.effect.getClass();
    }
}
