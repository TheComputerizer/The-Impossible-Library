package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_16_5 extends TILBasicBlock1_16_5 {
    
    public static TILBlockEntityProvider1_16_5 tileFrom(BlockProperties properties) {
        Material material = properties.getMaterial().unwrap();
        MaterialColor color = properties.getMaterialColor().unwrap();
        return new TILBlockEntityProvider1_16_5(Properties.of(material,color),properties);
    }
    
    public TILBlockEntityProvider1_16_5(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties,properties);
    }
    
    @Override public @Nullable TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return (TileEntity)this.properties.createBlockEntity(world instanceof IWorld ? WrapperHelper.wrapWorld((IWorld)world) : null,
                WrapperHelper.wrapState(state)).getEntity();
    }
    
    @Override public boolean hasTileEntity(BlockState state) {
        return true;
    }
}