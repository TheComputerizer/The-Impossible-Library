package mods.thecomputerizer.theimpossiblelibrary.api.util;

@SuppressWarnings("unchecked")
public interface Wrapped<W> {
    
    W getWrapped();
    
    default Class<? extends W> getWrappedClass() {
        return (Class<? extends W>)getWrapped().getClass();
    }
    
    default <U> U unwrap() {
        return (U)getWrapped();
    }
}