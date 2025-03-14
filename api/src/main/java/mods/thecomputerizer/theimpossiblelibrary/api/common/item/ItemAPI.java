package mods.thecomputerizer.theimpossiblelibrary.api.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;

public abstract class ItemAPI<I> extends AbstractWrapped<I> implements RegistryEntryAPI<I> {
    
    protected ResourceLocationAPI<?> registryName;

    protected ItemAPI(I item) {
        super(item);
    }
    
    public abstract ItemStackAPI<?> defaultStack();
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) this.registryName = getRegistry().getKey(unwrap());
        return this.registryName;
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}