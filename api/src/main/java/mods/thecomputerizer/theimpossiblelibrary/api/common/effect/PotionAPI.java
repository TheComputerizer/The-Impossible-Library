package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;

public abstract class PotionAPI<P> extends AbstractWrapped<P> implements RegistryEntryAPI<P> {
    
    protected ResourceLocationAPI<?> registryName;

    protected PotionAPI(Object potion) {
        super(potion);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName) && Objects.nonNull(this.wrapped)) {
            RegistryAPI<?> registry = getRegistry();
            if(Objects.nonNull(registry)) this.registryName = registry.getKey(unwrap());
        }
        return this.registryName;
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}