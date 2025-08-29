package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BlockProperty1_16_5<V extends Comparable<V>> extends BlockPropertyAPI<Property<V>,V> {
    
    public BlockProperty1_16_5(Object property) {
        super(property);
    }
    
    @Override public String asString(V value) {
        return getIfNotNull(w -> w.getName(value));
    }
    
    @Override public Collection<V> getAllowedValues() {
        return getIfNotNullOrDefault(Property::getPossibleValues, Collections.emptyList());
    }
    
    @Override public String getName() {
        return getIfNotNull(Property::getName);
    }
    
    @Override public Optional<V> parseValue(String unparsed) {
        return getIfNotNullOrDefault(w -> w.getValue(unparsed),Optional.empty());
    }
}
