package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import net.minecraft.block.Block;

public class Block1_16_5 extends BlockAPI<Block> {

    public Block1_16_5(Block block) {
        super(block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_16_5(this.wrapped.defaultBlockState());
    }
}