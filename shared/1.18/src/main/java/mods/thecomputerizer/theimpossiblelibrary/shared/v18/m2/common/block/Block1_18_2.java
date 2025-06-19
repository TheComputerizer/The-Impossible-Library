package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class Block1_18_2 extends BlockAPI<Block> {

    public Block1_18_2(Object block) {
        super(block instanceof Holder<?> ? (Block)((Holder<?>)block).value() : (Block)block);
    }

    @Override public BlockStateAPI<?> getDefaultState() {
        return new BlockState1_18_2(this.wrapped.defaultBlockState());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) Methods.invoke(this.wrapped,"setRegistryName",registryName.unwrap());
    }
}