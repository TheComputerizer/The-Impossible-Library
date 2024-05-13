package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;

import java.util.Collection;
import java.util.Optional;

@SuppressWarnings("unused") @Getter
public abstract class BlockPropertyAPI<P,V extends Comparable<V>> {
    
    protected final P property;
    
    protected BlockPropertyAPI(P property) {
        this.property = property;
    }
    
    public abstract String asString(V value);
    public abstract Collection<V> getAllowedValues();
    public abstract String getName();
    public abstract Class<V> getValueClass();
    
    public boolean isAllowedValue(V value) {
        return getAllowedValues().contains(value);
    }
    
    public abstract Optional<V> parseValue(String unparsed);
}