package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class EffectAPI<E> extends AbstractWrapped<E> implements RegistryEntryAPI<E> {
    
    protected ResourceLocationAPI<?> registryName;

    protected EffectAPI(E effect) {
        super(effect);
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}