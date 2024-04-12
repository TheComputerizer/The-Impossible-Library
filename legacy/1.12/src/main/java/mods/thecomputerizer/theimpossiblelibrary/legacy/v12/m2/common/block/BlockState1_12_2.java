package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockState1_12_2 extends BlockStateAPI<IBlockState> {

    public BlockState1_12_2(IBlockState state) {
        super(state);
    }

    @Override
    public BlockAPI<Block> getBlock() {
        return new Block1_12_2(this.state.getBlock());
    }

    @Override
    public MaterialAPI<?> getMaterial() {
        return new Material1_12_2(this.state.getMaterial());
    }
}
