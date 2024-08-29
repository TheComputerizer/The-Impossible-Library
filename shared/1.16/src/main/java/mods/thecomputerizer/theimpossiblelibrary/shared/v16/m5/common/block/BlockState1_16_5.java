package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;

import javax.annotation.Nullable;

public class BlockState1_16_5 extends BlockStateAPI<BlockState> {

    public BlockState1_16_5(BlockState state) {
        super(state);
    }

    @Override
    public BlockAPI<Block> getBlock() {
        return new Block1_16_5(this.state.getBlock());
    }

    @Override
    public MaterialAPI<?> getMaterial() {
        return new Material1_16_5(this.state.getMaterial());
    }
    
    @Nullable @Override public BlockProperty1_16_5<?> getProperty(String name) {
        for(Property<?> property : this.state.getProperties())
            if(property.getName().equals(name)) return new BlockProperty1_16_5<>(property);
        return null;
    }
    
    @SuppressWarnings("unchecked") @Override
    public boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property) {
        return this.state.getValue(((BlockProperty1_16_5<Boolean>)property).getProperty());
    }
    
    @SuppressWarnings("unchecked") @Override
    public <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property) {
        return this.state.getValue(((BlockProperty1_16_5<E>)property).getProperty());
    }
    
    @SuppressWarnings("unchecked") @Override
    public <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property) {
        return this.state.getValue(((BlockProperty1_16_5<V>)property).getProperty());
    }
    
    @SuppressWarnings("unchecked") @Override
    public <V extends Comparable<V>> BlockState1_16_5 withProperty(BlockPropertyAPI<?,V> property, V value) {
        return new BlockState1_16_5(this.state.setValue(((BlockProperty1_16_5<V>)property).getProperty(),value));
    }
}
