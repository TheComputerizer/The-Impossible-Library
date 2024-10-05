package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class MaterialColorAPI<C> extends AbstractWrapped<C> {
    
    protected MaterialColorAPI(C color) {
        super(color);
    }
    
    public abstract int getColor();
    public abstract int getID();
}