package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class ItemAPI<I> extends AbstractWrapped<I> implements RegistryEntryAPI<I> {
    
    protected ResourceLocationAPI<?> registryName;

    protected ItemAPI(I item) {
        super(item);
    }
    
    public abstract ItemStackAPI<?> defaultStack();
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}