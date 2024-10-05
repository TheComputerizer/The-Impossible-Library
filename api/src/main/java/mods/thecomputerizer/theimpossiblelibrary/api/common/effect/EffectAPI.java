package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class EffectAPI<E> extends AbstractWrapped<E> implements RegistryEntryAPI<E> {

    protected EffectAPI(E effect) {
        super(effect);
    }
}