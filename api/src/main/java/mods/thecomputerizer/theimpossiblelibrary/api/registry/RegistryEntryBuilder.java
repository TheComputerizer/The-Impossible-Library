package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

@Setter
public abstract class RegistryEntryBuilder<API> {
    
    protected ResourceLocationAPI<?> registryName;
    
    public abstract API build();
}