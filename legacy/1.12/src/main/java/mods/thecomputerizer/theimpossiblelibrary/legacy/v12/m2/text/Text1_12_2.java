package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public abstract class Text1_12_2 implements TextAPI<Style> {

    public abstract ITextComponent getComponent();
    
    @Override public String toString() {
        return getApplied();
    }
}