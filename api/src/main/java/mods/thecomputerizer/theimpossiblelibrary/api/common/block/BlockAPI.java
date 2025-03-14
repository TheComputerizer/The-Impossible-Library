package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;

public abstract class BlockAPI<B> extends AbstractWrapped<B> implements RegistryEntryAPI<B> {
    
    protected ResourceLocationAPI<?> registryName;
    
    protected BlockAPI(B block) {
        super(block);
    }
    
    @IndirectCallers public abstract BlockStateAPI<?> getDefaultState();
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) this.registryName = getRegistry().getKey(unwrap());
        return this.registryName;
    }
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}