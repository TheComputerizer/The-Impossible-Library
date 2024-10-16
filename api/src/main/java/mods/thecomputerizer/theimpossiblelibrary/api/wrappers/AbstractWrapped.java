package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import java.util.Objects;

public abstract class AbstractWrapped<W> implements Wrapped<W> {
    
    protected final W wrapped;
    
    protected AbstractWrapped(W wrapped) {
        this.wrapped = wrapped;
    }
    
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override public boolean equals(Object other) {
        if(Objects.isNull(this.wrapped)) return Objects.isNull(other);
        Object otherWrapped = other;
        while(otherWrapped instanceof Wrapped<?>) otherWrapped = ((Wrapped<?>)otherWrapped).getWrapped();
        return Objects.nonNull(otherWrapped) && this.wrapped.equals(otherWrapped);
    }
    
    @Override public W getWrapped() {
        return this.wrapped;
    }
    
    @SuppressWarnings("unchecked")
    public Class<? extends W> getWrappedClass() {
        return (Class<? extends W>)this.wrapped.getClass();
    }
    
    @Override public String toString() {
        return String.valueOf(this.wrapped);
    }
}