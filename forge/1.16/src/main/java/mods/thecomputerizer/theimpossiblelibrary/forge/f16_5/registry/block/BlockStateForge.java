package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BlockStateForge extends BlockForge implements BlockStateAPI<Block,BlockState> {

    private final BlockState state;

    public BlockStateForge(BlockState state) {
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
