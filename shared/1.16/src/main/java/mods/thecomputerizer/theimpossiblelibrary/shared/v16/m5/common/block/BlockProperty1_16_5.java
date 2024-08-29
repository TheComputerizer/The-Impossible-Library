package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import net.minecraft.state.Property;

import java.util.Collection;
import java.util.Optional;

public class BlockProperty1_16_5<V extends Comparable<V>> extends BlockPropertyAPI<Property<V>,V> {
    
    public BlockProperty1_16_5(Property<V> property) {
        super(property);
    }
    
    @Override public String asString(V value) {
        return this.property.getName(value);
    }
    
    @Override public Collection<V> getAllowedValues() {
        return this.property.getPossibleValues();
    }
    
    @Override public String getName() {
        return this.property.getName();
    }
    
    @Override public Class<V> getValueClass() {
        return this.property.getValueClass();
    }
    
    @Override public Optional<V> parseValue(String unparsed) {
        return this.property.getValue(unparsed);
    }
}
