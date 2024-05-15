package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;

import java.util.Iterator;

@SuppressWarnings("unused")
public interface ListTagAPI<T> extends Wrapped<T>, Iterable<BaseTagAPI<?>> {

    void addTag(BaseTagAPI<?> tag);
    void addTag(CompoundTagAPI<?> tag);
    void addTag(ListTagAPI<?> tag);
    void addTag(PrimitiveTagAPI<?> tag);
    void addTag(StringTagAPI<?> tag);
    Iterable<BaseTagAPI<?>> iterable();
    
    @SuppressWarnings("NullableProblems") @Override
    default Iterator<BaseTagAPI<?>> iterator() {
        return iterable().iterator();
    }
}