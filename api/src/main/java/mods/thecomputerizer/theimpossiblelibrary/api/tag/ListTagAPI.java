package mods.thecomputerizer.theimpossiblelibrary.api.tag;

import java.util.Iterator;

@SuppressWarnings("unused")
public abstract class ListTagAPI<T> extends BaseTagAPI<T> implements Iterable<BaseTagAPI<?>> {
    
    protected ListTagAPI(T tag) {
        super(tag);
    }
    
    public abstract void addTag(BaseTagAPI<?> tag);
    public abstract void addTag(CompoundTagAPI<?> tag);
    public abstract void addTag(ListTagAPI<?> tag);
    public abstract void addTag(PrimitiveTagAPI<?> tag);
    public abstract void addTag(StringTagAPI<?> tag);
    public abstract Iterable<BaseTagAPI<?>> iterable();
    
    @SuppressWarnings("NullableProblems") @Override
    public Iterator<BaseTagAPI<?>> iterator() {
        return iterable().iterator();
    }
}