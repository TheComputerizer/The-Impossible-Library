package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;

import javax.annotation.Nullable;
import java.util.Collection;

public abstract class BlockEntityBuilder1_16_5 extends BlockEntityBuilderAPI {
    
    protected BlockEntityBuilder1_16_5(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    protected <T> T[] buildBlockArray(Collection<BlockAPI<?>> blocks, Class<T> blockClass) {
        T[] array = ArrayHelper.create(blockClass,blocks.size());
        int i = 0;
        for(BlockAPI<?> block : blocks) {
            array[i] = block.unwrap();
            i++;
        }
        return array;
    }
}