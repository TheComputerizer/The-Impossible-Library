package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@SuppressWarnings("unused") @Getter
public abstract class BlockAPI<B> implements RegistryEntryAPI<B> {

    protected final B block;

    protected BlockAPI(B block) {
        this.block = block;
    }
    
    public abstract BlockStateAPI<?> getDefaultState();

    @Override
    public B getValue() {
        return this.block;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends B> getValueClass() {
        return (Class<? extends B>)this.block.getClass();
    }
}