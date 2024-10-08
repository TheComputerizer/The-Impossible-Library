package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

public interface Wrapped<W> {
    
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