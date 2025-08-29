package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class BlockState1_20 extends BlockStateAPI<BlockState> {

    public BlockState1_20(Object state) {
        super(state);
    }
    
    @Override public BlockAPI<?> getBlock() {
        return getIfNotNull(w -> WrapperHelper.wrapBlock(w.getBlock()));
    }

    @Override public MaterialAPI<?> getMaterial() { //The material class isn't standalone in 1.20+
        return getIfNotNull(WrapperHelper::wrapMaterial);
    }
    
    @Override public @Nullable BlockPropertyAPI<?,?> getProperty(String name) {
        Collection<Property<?>> keys = getIfNotNullOrDefault(BlockState::getProperties, Collections.emptyList());
        for(Property<?> property : keys)
            if(property.getName().equals(name)) return new BlockProperty1_20<>(property);
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
        return getIfNotNull(w -> WrapperHelper.wrapState(w.setValue(property.unwrap(),value)));
    }
}