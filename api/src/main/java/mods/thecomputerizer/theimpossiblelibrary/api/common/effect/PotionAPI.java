package mods.thecomputerizer.theimpossiblelibrary.api.common.effect;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class PotionAPI<P> extends AbstractWrapped<P> implements RegistryEntryAPI<P> {
    
    protected ResourceLocationAPI<?> registryName;

    protected PotionAPI(P potion) {
        super(potion);
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}