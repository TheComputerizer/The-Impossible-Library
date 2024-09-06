package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.TextHelper1_16_5;
import net.minecraft.util.text.Style;

public class TextHelperForge1_16_5 extends TextHelper1_16_5<Style> {

    private final TextStyleForge1_16_5 style;

    public TextHelperForge1_16_5() {
        this.style = new TextStyleForge1_16_5();
    }
    
    @Override public TextStringForge1_16_5 getLiteral(String text) {
        return new TextStringForge1_16_5(this,text);
    }

    @Override public TextStyleForge1_16_5 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslationForge1_16_5 getTranslated(String key, Object... args) {
        return new TextTranslationForge1_16_5(this,key,args);
    }
}