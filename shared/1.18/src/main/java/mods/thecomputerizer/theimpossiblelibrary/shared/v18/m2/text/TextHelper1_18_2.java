package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import net.minecraft.network.chat.Style;

public class TextHelper1_18_2 implements TextHelperAPI<Style> {

    private final TextStyle1_18_2 style;

    public TextHelper1_18_2() {
        this.style = new TextStyle1_18_2();
    }
    
    @Override public TextString1_18_2 getLiteral(String text) {
        return new TextString1_18_2(this, text);
    }

    @Override public TextStyle1_18_2 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslation1_18_2 getTranslated(String key, Object... args) {
        return new TextTranslation1_18_2(this, key, args);
    }
}