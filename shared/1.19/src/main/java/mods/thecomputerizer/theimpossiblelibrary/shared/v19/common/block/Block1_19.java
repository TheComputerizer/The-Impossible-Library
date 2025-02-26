package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import net.minecraft.world.level.block.Block;

public class Block1_19 extends BlockAPI<Block> {

    public Block1_19(Object block) {
        super((Block)block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_19(this.wrapped.defaultBlockState());
    }
}