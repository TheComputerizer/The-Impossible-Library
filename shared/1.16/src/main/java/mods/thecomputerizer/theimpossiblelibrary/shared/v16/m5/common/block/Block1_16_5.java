package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.world.level.block.Block;

public class Block1_16_5 extends BlockAPI<Block> {

    public Block1_16_5(Object block) {
        super(block);
    }
    
    @Override public BlockStateAPI<?> getDefaultState() {
        return getIfNotNull(w -> WrapperHelper.wrapState(w.defaultBlockState()));
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        setForgeRegistryName(this,registryName);
    }
}