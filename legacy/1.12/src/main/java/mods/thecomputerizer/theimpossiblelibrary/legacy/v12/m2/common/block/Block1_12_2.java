package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class Block1_12_2 extends BlockAPI<Block> {

    public Block1_12_2(Object block) {
        super((Block)block);
    }
    
    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_12_2(this.wrapped.getDefaultState());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}