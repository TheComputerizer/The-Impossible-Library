package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;

public class BlockState1_18_2 extends BlockStateAPI<BlockState> {

    public BlockState1_18_2(Object state) {
        super((BlockState)state);
    }

    @Override public BlockAPI<Block> getBlock() {
        return WrapperHelper.wrapBlock(this.wrapped.getBlock());
    }

    @Override public MaterialAPI<?> getMaterial() {
        return WrapperHelper.wrapMaterial(this.wrapped.getMaterial());
    }
    
    @Nullable @Override public BlockProperty1_18_2<?> getProperty(String name) {
        for(Property<?> property : this.wrapped.getProperties())
            if(property.getName().equals(name)) return new BlockProperty1_18_2<>(property);
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