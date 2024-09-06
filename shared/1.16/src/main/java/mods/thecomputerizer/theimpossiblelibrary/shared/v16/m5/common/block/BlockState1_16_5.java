package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;

import javax.annotation.Nullable;

public class BlockState1_16_5 extends BlockStateAPI<BlockState> {

    public BlockState1_16_5(Object state) {
        super((BlockState)state);
    }

    @Override public BlockAPI<Block> getBlock() {
        return WrapperHelper.wrapBlock(this.wrapped.getBlock());
    }

    @Override public MaterialAPI<?> getMaterial() {
        return WrapperHelper.wrapMaterial(this.wrapped.getMaterial());
    }
    
    @Nullable @Override public BlockProperty1_16_5<?> getProperty(String name) {
        for(Property<?> property : this.wrapped.getProperties())
            if(property.getName().equals(name)) return new BlockProperty1_16_5<>(property);
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
    
    @Override public <V extends Comparable<V>> BlockStateAPI<BlockState> withProperty(BlockPropertyAPI<?,V> property, V value) {
        return WrapperHelper.wrapState(this.wrapped.setValue(property.unwrap(),value));
    }
}
