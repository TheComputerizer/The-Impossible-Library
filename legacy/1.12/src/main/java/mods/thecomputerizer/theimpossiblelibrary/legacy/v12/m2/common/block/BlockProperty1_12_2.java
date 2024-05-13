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
        return this.property.getName(value);
    }
    
    @Override public Collection<V> getAllowedValues() {
        return this.property.getAllowedValues();
    }
    
    @Override public String getName() {
        return this.property.getName();
    }
    
    @Override public Class<V> getValueClass() {
        return this.property.getValueClass();
    }
    
    @Override public Optional<V> parseValue(String unparsed) {
        return this.property.parseValue(unparsed).toJavaUtil();
    }
}
