package mods.thecomputerizer.theimpossiblelibrary.api.tag;

public abstract class StringTagAPI<T> extends BaseTagAPI<T> {
    
    protected StringTagAPI(T tag) {
        super(tag);
    }
    
    public abstract String getValue();
}