package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.block;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockState1_16_5 extends Block1_16_5 implements BlockStateAPI<BlockState> {

    private final BlockState state;

    public BlockState1_16_5(BlockState state) {
        super(state.getBlock());
        this.state = state;
    }

    @Override
    public BlockAPI<Block> getBlockAPI() {
        return this;
    }

    @Override
    public BlockState getState() {
        return this.state;
    }
}
