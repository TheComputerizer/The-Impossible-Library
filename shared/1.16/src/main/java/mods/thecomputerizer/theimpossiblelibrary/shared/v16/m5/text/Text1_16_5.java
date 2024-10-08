package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

import static net.minecraft.util.text.TextFormatting.RESET;

public abstract class Text1_16_5 implements TextAPI<Style> {
    
    @Override public String getAppliedNoReset() {
        String applied = getApplied();
        String resetStr = RESET.toString();
        return applied.endsWith(resetStr) ? applied.substring(0,applied.length()-resetStr.length()) : applied;
    }
    
    @SuppressWarnings("unchecked") @Override public ITextComponent getAsComponent() {
        return getComponent();
    }
    
    public abstract ITextComponent getComponent();
    
    @Override public String toString() {
        return getAppliedNoReset();
    }
}