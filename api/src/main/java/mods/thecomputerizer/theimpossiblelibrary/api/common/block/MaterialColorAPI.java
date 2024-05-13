package mods.thecomputerizer.theimpossiblelibrary.api.common.block;

import lombok.Getter;

@Getter
public abstract class MaterialColorAPI<C> {
    
    protected final C materialColor;
    
    protected MaterialColorAPI(C color) {
        this.materialColor = color;
    }
    
    public abstract int getColor();
    public abstract int getID();
}
