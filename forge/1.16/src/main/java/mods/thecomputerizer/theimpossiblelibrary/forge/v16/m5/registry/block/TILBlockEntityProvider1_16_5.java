package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity.BlockEntity1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiFunction;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_16_5 extends TILBasicBlock1_16_5 {
    
    protected final BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator;
    
    public TILBlockEntityProvider1_16_5(Properties properties,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer,
            BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator) {
        super(properties,stateTransformer);
        this.blockEntityCreator = blockEntityCreator;
    }
    
    @Nullable @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ((BlockEntity1_16_5)this.blockEntityCreator.apply(
                world instanceof IWorld ? new World1_16_5((IWorld)world) : null,new BlockState1_16_5(state))).getEntity();
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
