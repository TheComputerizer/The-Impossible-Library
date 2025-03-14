package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;

public abstract class PotionAPI<P> extends AbstractWrapped<P> implements RegistryEntryAPI<P> {
    
    protected ResourceLocationAPI<?> registryName;

    protected PotionAPI(P potion) {
        super(potion);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) this.registryName = getRegistry().getKey(unwrap());
        return this.registryName;
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}