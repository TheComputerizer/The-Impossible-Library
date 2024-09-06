package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class BlockAPI<B> extends AbstractWrapped<B> implements RegistryEntryAPI<B> {

    protected BlockAPI(B block) {
        super(block);
    }
    
    @IndirectCallers public abstract BlockStateAPI<?> getDefaultState();
}