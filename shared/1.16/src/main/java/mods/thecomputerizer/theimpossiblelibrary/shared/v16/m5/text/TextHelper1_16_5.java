package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import net.minecraft.util.text.Style;

public class TextHelper1_16_5 implements TextHelperAPI<Style> {

    private final TextStyle1_16_5 style;

    public TextHelper1_16_5() {
        this.style = new TextStyle1_16_5();
    }
    
    @Override public TextString1_16_5 getLiteral(String text) {
        return new TextString1_16_5(this, text);
    }

    @Override public TextStyle1_16_5 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslation1_16_5 getTranslated(String key, Object... args) {
        return new TextTranslation1_16_5(this, key, args);
    }
}