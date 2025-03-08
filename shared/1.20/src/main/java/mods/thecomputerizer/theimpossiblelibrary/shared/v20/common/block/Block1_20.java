package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import net.minecraft.world.level.block.Block;

public class Block1_20 extends BlockAPI<Block> {

    public Block1_20(Object block) {
        super((Block)block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_20(this.wrapped.defaultBlockState());
    }
}