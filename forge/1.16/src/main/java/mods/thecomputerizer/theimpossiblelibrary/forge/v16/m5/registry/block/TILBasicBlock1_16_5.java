package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class TILBasicBlock1_16_5 extends Block {
    
    public static Collection<Property<?>> stateProperties = Collections.emptyList();
    
    public TILBasicBlock1_16_5(Properties properties,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer) {
        super(properties);
        if(Objects.nonNull(stateTransformer))
            registerDefaultState(((BlockState1_16_5)stateTransformer.apply(
                    new BlockState1_16_5(this.stateDefinition.any()))).getState());
        
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
}
