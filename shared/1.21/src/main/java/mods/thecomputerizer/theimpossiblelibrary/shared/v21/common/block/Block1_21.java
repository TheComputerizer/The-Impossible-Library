package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

public class Block1_21 extends BlockAPI<Block> {

    public Block1_21(Object block) {
        super(block instanceof Holder<?> ? ((Holder<?>)block).value() : block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_21(this.wrapped.defaultBlockState());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}