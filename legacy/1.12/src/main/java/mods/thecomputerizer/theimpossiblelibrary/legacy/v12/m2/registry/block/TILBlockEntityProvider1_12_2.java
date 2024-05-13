package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiFunction;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_12_2 extends TILBasicBlock1_12_2 implements ITileEntityProvider {
    
    protected final BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator;
    
    public TILBlockEntityProvider1_12_2(Material material, MapColor color,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer,
            BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator) {
        super(material,color,stateTransformer);
        this.blockEntityCreator = blockEntityCreator;
    }
    
    @SuppressWarnings("deprecation") @Nullable @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return ((BlockEntity1_12_2)this.blockEntityCreator.apply(
                new World1_12_2(world),new BlockState1_12_2(getStateFromMeta(meta)))).getEntity();
    }
}
