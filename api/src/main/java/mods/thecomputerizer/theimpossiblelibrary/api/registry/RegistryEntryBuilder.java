package mods.thecomputerizer.theimpossiblelibrary.api.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public abstract class RegistryEntryBuilder<API> {
    
    protected static final boolean NAMED_ENV = CoreAPI.isNamedEnv();
    protected static final boolean SRG_ENV = CoreAPI.isSrgEnv();
    
    protected ResourceLocationAPI<?> registryName;
    
    public abstract API build();
    
    public RegistryEntryBuilder<API> setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}