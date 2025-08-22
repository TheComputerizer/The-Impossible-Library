package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import java.util.Objects;

public abstract class AbstractWrapped<W> implements Wrapped<W> {
    
    protected final W wrapped;
    
    protected AbstractWrapped(W wrapped) {
        this.wrapped = wrapped;
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