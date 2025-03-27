package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

public class Block1_19 extends BlockAPI<Block> {

    public Block1_19(Object block) {
        super(block instanceof Holder<?> ? (Block)((Holder<?>)block).value() : (Block)block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_19(this.wrapped.defaultBlockState());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}