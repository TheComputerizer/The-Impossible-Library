package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.Text1_16_5;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

import static net.minecraft.util.text.TextFormatting.RESET;

public abstract class TextForge1_16_5 extends Text1_16_5<Style> {
    
    @Override public String getAppliedNoReset() {
        String applied = getApplied();
        String resetStr = RESET.toString();
        return applied.endsWith(resetStr) ? applied.substring(0,applied.length()-resetStr.length()) : applied;
    }
    
    @SuppressWarnings("unchecked") @Override
    public ITextComponent getAsComponent() {
        return getComponent();
    }
    
    public abstract ITextComponent getComponent();
    
    @Override public String toString() {
        return getAppliedNoReset();
    }
}