package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
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
    
    @Override public @Nullable TileEntity createNewTileEntity(World world, int meta) {
        return this.properties.createBlockEntity(WrapperHelper.wrapWorld(world),
                WrapperHelper.wrapState(getStateFromMeta(meta))).unwrap();
    }
}
