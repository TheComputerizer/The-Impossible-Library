package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text.TextHelper1_16_5;
import net.minecraft.network.chat.Style;

public class TextHelperFabric1_16_5 extends TextHelper1_16_5<Style> {

    private final TextStyleFabric1_16_5 style;

    public TextHelperFabric1_16_5() {
        this.style = new TextStyleFabric1_16_5();
    }
    
    @Override public TextStringFabric1_16_5 getLiteral(String text) {
        return new TextStringFabric1_16_5(this,text);
    }

    @Override public TextStyleFabric1_16_5 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslationFabric1_16_5 getTranslated(String key, Object... args) {
        return new TextTranslationFabric1_16_5(this,key,args);
    }
}