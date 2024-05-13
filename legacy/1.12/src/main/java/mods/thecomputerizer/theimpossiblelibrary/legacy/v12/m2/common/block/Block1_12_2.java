package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.block.Block;

public class Block1_12_2 extends BlockAPI<Block> {

    public Block1_12_2(Block block) {
        super(block);
    }
    
    @Override
    public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_12_2(this.block.getDefaultState());
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_12_2(this.block.getRegistryName());
    }
}