package mods.thecomputerizer.theimpossiblelibrary.api.util;

import java.util.Objects;

public abstract class MutableWrapped<W> implements Wrapped<W> {
    
    protected W wrapped;
    
    protected MutableWrapped(W wrapped) {
        this.wrapped = wrapped;
    }
    
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override
    public boolean equals(Object other) {
        if(Objects.isNull(this.wrapped)) return Objects.isNull(other);
        Object otherWrapped = other;
        while(otherWrapped instanceof Wrapped<?>) otherWrapped = ((Wrapped<?>)otherWrapped).getWrapped();
        return Objects.nonNull(otherWrapped) && this.wrapped.equals(otherWrapped);
    }
    
    @Override public W getWrapped() {
        return this.wrapped;
    }
    
    public MutableWrapped<W> setWrapped(W wrapped) {
        this.wrapped = wrapped;
        return this;
    }
    
    @Override public String toString() {
        return String.valueOf(this.wrapped);
    }
}
