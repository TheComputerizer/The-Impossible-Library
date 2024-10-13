package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import static net.minecraft.ChatFormatting.RESET;

public abstract class Text1_18_2 implements TextAPI<Style> {
    
    @Override public String getAppliedNoReset() {
        String applied = getApplied();
        String resetStr = RESET.toString();
        return applied.endsWith(resetStr) ? applied.substring(0,applied.length()-resetStr.length()) : applied;
    }
    
    @SuppressWarnings("unchecked") @Override public Component getAsComponent() {
        return getComponent();
    }
    
    public abstract Component getComponent();
    
    @Override public String toString() {
        return getAppliedNoReset();
    }
}