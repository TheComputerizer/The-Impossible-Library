package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockState1_16_5 extends BlockStateAPI<BlockState> {

    public BlockState1_16_5(BlockState state) {
        super(state);
    }

    @Override
    public BlockAPI<Block> getBlock() {
        return new Block1_16_5(this.state.getBlock());
    }

    @Override
    public MaterialAPI<?> getMaterial() {
        return new Material1_16_5(this.state.getMaterial());
    }
}
