package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class BlockState1_12_2 extends BlockStateAPI<IBlockState> {

    public BlockState1_12_2(Object state) {
        super(state);
    }

    @Override public BlockAPI<?> getBlock() {
        return getIfNotNull(w -> WrapperHelper.wrapBlock(w.getBlock()));
    }

    @Override public MaterialAPI<?> getMaterial() {
        return getIfNotNull(w -> WrapperHelper.wrapMaterial(w.getMaterial()));
    }
    
    @Override public @Nullable BlockPropertyAPI<?,?> getProperty(String name) {
        Collection<IProperty<?>> keys = getIfNotNullOrDefault(IBlockState::getPropertyKeys,Collections.emptyList());
        for(IProperty<?> property : keys)
            if(property.getName().equals(name)) return new BlockProperty1_12_2<>(property);
        return null;
    }
    
    @Override public boolean getPropertyBool(BlockPropertyAPI<?,Boolean> property) {
        return getIfNotNullOrDefault(w -> w.getValue(property.unwrap()),false);
    }
    
    @Override public <E extends Enum<E>> E getPropertyEnum(BlockPropertyAPI<?,E> property) {
        return getIfNotNull(w -> w.getValue(property.unwrap()));
    }
    
    @Override public <V extends Comparable<V>> V getPropertyValue(BlockPropertyAPI<?,V> property) {
        return getIfNotNull(w -> w.getValue(property.unwrap()));
    }
    
    @Override public <V extends Comparable<V>> BlockStateAPI<?> withProperty(BlockPropertyAPI<?,V> property, V value) {
        return getIfNotNull(w -> WrapperHelper.wrapState(w.withProperty(property.unwrap(),value)));
    }
}