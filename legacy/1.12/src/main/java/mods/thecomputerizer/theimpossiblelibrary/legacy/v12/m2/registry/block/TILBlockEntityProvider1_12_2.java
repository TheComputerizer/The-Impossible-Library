package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI.ZERO;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_12_2 extends TILBasicBlock1_12_2 implements ITileEntityProvider {
    
    public TILBlockEntityProvider1_12_2(BlockProperties properties) {
        super(properties);
    }
    
    @Override public @Nullable TileEntity createNewTileEntity(World world, int meta) {
        return (TileEntity)this.properties.createBlockEntity(WrapperHelper.wrapWorld(world),ZERO,
                WrapperHelper.wrapState(getStateFromMeta(meta))).getEntity();
    }
}