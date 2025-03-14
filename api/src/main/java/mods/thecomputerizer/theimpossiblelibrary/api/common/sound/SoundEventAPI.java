package mods.thecomputerizer.theimpossiblelibrary.api.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;

public abstract class SoundEventAPI<S> extends AbstractWrapped<S> implements RegistryEntryAPI<S> {
    
    protected ResourceLocationAPI<?> registryName;

    protected SoundEventAPI(S sound) {
        super(sound);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) this.registryName = getRegistry().getKey(unwrap());
        return this.registryName;
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}