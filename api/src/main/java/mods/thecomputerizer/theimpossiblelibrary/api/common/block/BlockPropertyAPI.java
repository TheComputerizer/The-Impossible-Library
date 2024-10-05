package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Collection;
import java.util.Optional;

public abstract class BlockPropertyAPI<P,V extends Comparable<V>> extends AbstractWrapped<P> {
    
    protected BlockPropertyAPI(P property) {
        super(property);
    }
    
    @IndirectCallers public abstract String asString(V value);
    public abstract Collection<V> getAllowedValues();
    public abstract String getName();
    
    @IndirectCallers
    public boolean isAllowedValue(V value) {
        return getAllowedValues().contains(value);
    }
    
    @IndirectCallers public abstract Optional<V> parseValue(String unparsed);
}