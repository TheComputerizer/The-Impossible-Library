package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import net.minecraft.block.properties.IProperty;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class BlockProperty1_12_2<V extends Comparable<V>> extends BlockPropertyAPI<IProperty<V>,V> {
    
    public BlockProperty1_12_2(Object property) {
        super(property);
    }
    
    @Override public String asString(V value) {
        return getIfNotNull(IProperty::getName);
    }
    
    @Override public Collection<V> getAllowedValues() {
        return getIfNotNullOrDefault(IProperty::getAllowedValues,Collections.emptyList());
    }
    
    @Override public String getName() {
        return getIfNotNull(IProperty::getName);
    }
    
    @Override public Optional<V> parseValue(String unparsed) {
        return getIfNotNullOrDefault(w -> w.parseValue(unparsed).toJavaUtil(),Optional.empty());
    }
}
