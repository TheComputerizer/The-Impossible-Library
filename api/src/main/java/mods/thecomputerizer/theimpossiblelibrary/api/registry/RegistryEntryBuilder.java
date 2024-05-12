package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public abstract class RegistryEntryBuilder<API> {
    
    protected ResourceLocationAPI<?> registryName;
    
    public abstract API build();
    
    public RegistryEntryBuilder<API> setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}