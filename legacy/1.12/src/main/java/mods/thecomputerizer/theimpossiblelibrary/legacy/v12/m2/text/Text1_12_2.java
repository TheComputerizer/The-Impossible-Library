package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

import static net.minecraft.util.text.TextFormatting.RESET;

public abstract class Text1_12_2 implements TextAPI<Style> {
    
    @Override public String getAppliedNoReset() {
        String applied = getApplied();
        String resetStr = RESET.toString();
        return applied.endsWith(resetStr) ? applied.substring(0,applied.length()-resetStr.length()) : applied;
    }
    
    public abstract ITextComponent getComponent();
    
    @Override public String toString() {
        return getAppliedNoReset();
    }
}