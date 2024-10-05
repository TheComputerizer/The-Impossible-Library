package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;

public class BlockState1_12_2 extends BlockStateAPI<IBlockState> {

    public BlockState1_12_2(Object state) {
        super((IBlockState)state);
    }

    @Override public BlockAPI<Block> getBlock() {
        return WrapperHelper.wrapBlock(this.wrapped.getBlock());
    }

    @Override public MaterialAPI<Material> getMaterial() {
        return WrapperHelper.wrapMaterial(this.wrapped.getMaterial());
    }
    
    @Override public @Nullable BlockProperty1_12_2<?> getProperty(String name) {
        for(IProperty<?> property : this.wrapped.getPropertyKeys())
            if(property.getName().equals(name)) return new BlockProperty1_12_2<>(property);
        return null;
    }
    
    @Override public boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property) {
        return this.wrapped.getValue(property.unwrap());
    }
    
    @Override public <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property) {
        return this.wrapped.getValue(property.unwrap());
    }
    
    @Override public <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property) {
        return this.wrapped.getValue(property.unwrap());
    }
    
    @Override public <V extends Comparable<V>> BlockStateAPI<IBlockState> withProperty(BlockPropertyAPI<?,V> property, V value) {
        return WrapperHelper.wrapState(this.wrapped.withProperty(property.unwrap(),value));
    }
}