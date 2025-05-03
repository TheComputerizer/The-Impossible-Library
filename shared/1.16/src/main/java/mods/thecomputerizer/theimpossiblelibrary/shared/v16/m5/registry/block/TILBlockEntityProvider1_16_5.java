package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI.ZERO;

public class TILBlockEntityProvider1_16_5 extends TILBasicBlock1_16_5 {
    
    public static TILBlockEntityProvider1_16_5 tileFrom(BlockProperties properties) {
        Material material = properties.getMaterial().unwrap();
        MaterialColor color = properties.getMaterialColor().unwrap();
        return new TILBlockEntityProvider1_16_5(Properties.of(material,color),properties);
    }
    
    public TILBlockEntityProvider1_16_5(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties,properties);
    }
    
    @Override public @Nullable BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        BlockEntity
        return (BlockEntity)this.properties.createBlockEntity(world instanceof LevelAccessor ? WrapperHelper.wrapWorld(world) : null,
                                                             ZERO,WrapperHelper.wrapState(state)).getEntity();
    }
    
    @Override public boolean hasTileEntity(BlockState state) {
        return true;
    }
}