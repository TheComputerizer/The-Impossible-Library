package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

public class TILBasicBlock1_12_2 extends Block {
    
    public TILBasicBlock1_12_2(Material material, MapColor color,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer) {
        super(material,color);
        if(Objects.nonNull(stateTransformer))
            setDefaultState(((BlockState1_12_2)stateTransformer.apply(new BlockState1_12_2(getDefaultState()))).getState());
    }
}
