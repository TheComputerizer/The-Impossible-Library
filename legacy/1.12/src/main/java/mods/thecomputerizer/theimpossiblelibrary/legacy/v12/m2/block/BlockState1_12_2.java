package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.block;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockState1_12_2 extends Block1_12_2 implements BlockStateAPI<IBlockState> {

    private final IBlockState state;

    public BlockState1_12_2(IBlockState state) {
        super(state.getBlock());
        this.state = state;
    }

    @Override
    public BlockAPI<Block> getBlockAPI() {
        return this;
    }

    @Override
    public IBlockState getState() {
        return this.state;
    }
}
