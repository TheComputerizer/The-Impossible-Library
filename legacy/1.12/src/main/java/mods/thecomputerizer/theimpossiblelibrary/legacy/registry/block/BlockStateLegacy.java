package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class BlockStateLegacy extends BlockLegacy implements BlockStateAPI<IBlockState> {

    private final IBlockState state;

    public BlockStateLegacy(IBlockState state) {
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
