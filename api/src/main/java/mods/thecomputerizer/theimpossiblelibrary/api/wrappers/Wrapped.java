package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;

public interface Wrapped<W> extends CoreStateAccessor {
    
    W getWrapped();
    
    @SuppressWarnings("unchecked")
    default Class<? extends W> getWrappedClass() {
        return (Class<? extends W>)getWrapped().getClass();
    }
    
    @SuppressWarnings("unchecked")
    default <U> U unwrap() {
        return (U)getWrapped();
    }
}