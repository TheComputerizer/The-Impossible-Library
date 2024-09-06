package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import net.minecraft.block.properties.IProperty;

import java.util.Collection;
import java.util.Optional;

public class BlockProperty1_12_2<V extends Comparable<V>> extends BlockPropertyAPI<IProperty<V>,V> {
    
    public BlockProperty1_12_2(IProperty<V> property) {
        super(property);
    }
    
    @Override public String asString(V value) {
        return this.wrapped.getName(value);
    }
    
    @Override public Collection<V> getAllowedValues() {
        return this.wrapped.getAllowedValues();
    }
    
    @Override public String getName() {
        return this.wrapped.getName();
    }
    
    @Override public Optional<V> parseValue(String unparsed) {
        return this.wrapped.parseValue(unparsed).toJavaUtil();
    }
}
