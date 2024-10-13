package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import net.minecraft.world.level.block.Block;

public class Block1_18_2 extends BlockAPI<Block> {

    public Block1_18_2(Object block) {
        super((Block)block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_18_2(this.wrapped.defaultBlockState());
    }
}