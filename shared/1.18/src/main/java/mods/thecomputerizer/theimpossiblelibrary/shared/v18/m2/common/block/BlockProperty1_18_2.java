package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.Optional;

public class BlockProperty1_18_2<V extends Comparable<V>> extends BlockPropertyAPI<Property<V>,V> {
    
    public BlockProperty1_18_2(Property<V> property) {
        super(property);
    }
    
    @Override public String asString(V value) {
        return this.wrapped.getName(value);
    }
    
    @Override public Collection<V> getAllowedValues() {
        return this.wrapped.getPossibleValues();
    }
    
    @Override public String getName() {
        return this.wrapped.getName();
    }
    
    @Override public Optional<V> parseValue(String unparsed) {
        return this.wrapped.getValue(unparsed);
    }
}
