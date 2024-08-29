package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.World1_16_5;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_16_5 extends TILBasicBlock1_16_5 {
    
    public TILBlockEntityProvider1_16_5(BlockProperties properties) {
        super(properties);
    }
    
    @Nullable @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return (TileEntity)this.properties.createBlockEntity(world instanceof IWorld ? new World1_16_5((IWorld)world) : null,
                new BlockState1_16_5(state)).getEntity();
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
