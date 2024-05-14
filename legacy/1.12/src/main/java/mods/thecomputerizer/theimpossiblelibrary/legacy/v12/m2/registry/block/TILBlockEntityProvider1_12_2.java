package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_12_2 extends TILBasicBlock1_12_2 implements ITileEntityProvider {
    
    public TILBlockEntityProvider1_12_2(BlockProperties properties) {
        super(properties);
    }
    
    @Nullable @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return (TileEntity)this.properties.createBlockEntity(
                new World1_12_2(world),new BlockState1_12_2(getStateFromMeta(meta))).getEntity();
    }
}
