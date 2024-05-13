package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;

public class BlockState1_12_2 extends BlockStateAPI<IBlockState> {

    public BlockState1_12_2(IBlockState state) {
        super(state);
    }

    @Override
    public Block1_12_2 getBlock() {
        return new Block1_12_2(this.state.getBlock());
    }

    @Override
    public Material1_12_2 getMaterial() {
        return new Material1_12_2(this.state.getMaterial());
    }
    
    @Nullable @Override public BlockProperty1_12_2<?> getProperty(String name) {
        for(IProperty<?> property : this.state.getPropertyKeys())
            if(property.getName().equals(name)) return new BlockProperty1_12_2<>(property);
        return null;
    }
    
    @SuppressWarnings("unchecked") @Override
    public boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property) {
        return this.state.getValue(((BlockProperty1_12_2<Boolean>)property).getProperty());
    }
    
    @SuppressWarnings("unchecked") @Override
    public <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property) {
        return this.state.getValue(((BlockProperty1_12_2<E>)property).getProperty());
    }
    
    @SuppressWarnings("unchecked") @Override
    public <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property) {
        return this.state.getValue(((BlockProperty1_12_2<V>)property).getProperty());
    }
    
    @SuppressWarnings("unchecked") @Override
    public <V extends Comparable<V>> BlockState1_12_2 withProperty(BlockPropertyAPI<?,V> property, V value) {
        return new BlockState1_12_2(this.state.withProperty(((BlockProperty1_12_2<V>)property).getProperty(),value));
    }
}
