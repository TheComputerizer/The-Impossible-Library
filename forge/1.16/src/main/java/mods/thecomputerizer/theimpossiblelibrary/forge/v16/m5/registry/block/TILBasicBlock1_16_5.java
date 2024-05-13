package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import net.minecraft.block.Block;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

public class TILBasicBlock1_16_5 extends Block {
    
    public TILBasicBlock1_16_5(Properties properties, @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer) {
        super(properties);
        if(Objects.nonNull(stateTransformer))
            registerDefaultState(((BlockState1_16_5)stateTransformer.apply(
                    new BlockState1_16_5(this.stateDefinition.any()))).getState());
    }
}
