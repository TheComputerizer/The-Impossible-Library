package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class Block1_12_2 extends BlockAPI<Block> {

    public Block1_12_2(Object block) {
        super(block);
    }
    
    @Override public BlockStateAPI<?> getDefaultState() {
        return getIfNotNull(w -> WrapperHelper.wrapState(w.getDefaultState()));
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(Objects.nonNull(this.wrapped))
            this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}