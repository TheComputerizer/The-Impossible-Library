package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.Text1_16_5;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import static net.minecraft.ChatFormatting.RESET;

public abstract class TextFabric1_16_5 extends Text1_16_5<Style> {
    
    @Override public String getAppliedNoReset() {
        String applied = getApplied();
        String resetStr = RESET.toString();
        return applied.endsWith(resetStr) ? applied.substring(0,applied.length()-resetStr.length()) : applied;
    }
    
    @SuppressWarnings("unchecked") @Override public MutableComponent getAsComponent() {
        return getComponent();
    }
    
    public abstract MutableComponent getComponent();
    
    @Override public String toString() {
        return getAppliedNoReset();
    }
}