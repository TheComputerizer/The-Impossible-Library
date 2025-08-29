package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import java.util.Objects;
import java.util.Optional;

public abstract class MutableWrapped<W> implements Wrapped<W> {
    
    protected W wrapped;
    
    protected MutableWrapped() {
        this(null);
    }
    
    protected MutableWrapped(Object wrapped) {
        this.wrapped = GenericUtils.cast(wrapped);
    }
    
    public Optional<W> asOptional() {
        return Optional.ofNullable(this.wrapped);
    }
    
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override public boolean equals(Object other) {
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