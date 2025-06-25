package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.world.level.block.Block;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class Block1_16_5 extends BlockAPI<Block> {

    public Block1_16_5(Object block) {
        super((Block)block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_16_5(this.wrapped.defaultBlockState());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) Methods.invoke(this.wrapped,"setRegistryName",(Object)registryName.unwrap());
    }
}