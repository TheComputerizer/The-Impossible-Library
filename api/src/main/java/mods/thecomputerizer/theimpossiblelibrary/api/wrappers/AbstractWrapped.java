package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import java.util.Objects;

//TODO Add generic multi arg wrapper
//TODO Figure out how to do a proper API inheritence system:
//  - Current API classes & methods would stay mostly the same
//  - Instead of each version extending an API class separately, the implementation from the previous version would be used as a base
//  - Only overwrites for the current version would be needed
//  - There needs to be a way of dealing with conflicting signatures that result from class name & method arg differences
public abstract class AbstractWrapped<W> implements Wrapped<W> {
    
    protected final W wrapped;
    
    protected AbstractWrapped(Object wrapped) {
        this.wrapped = GenericUtils.cast(wrapped);
    }
    
    @Override public boolean equals(Object other) {
        if(Objects.isNull(other)) return Objects.isNull(this.wrapped);
        while(other instanceof Wrapped<?>) other = ((Wrapped<?>)other).getWrapped();
        return Objects.isNull(other) ? Objects.isNull(this.wrapped) : this.wrapped.equals(other);
    }
    
    @Override public W getWrapped() {
        return this.wrapped;
    }
    
    public Class<W> getWrappedClass() {
        return GenericUtils.cast(this.wrapped.getClass());
    }
    
    @Override public String toString() {
        return String.valueOf(this.wrapped);
    }
}