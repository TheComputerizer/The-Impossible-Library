package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import net.minecraft.util.text.Style;

public class TextHelper1_12_2 implements TextHelperAPI<Style> {

    private final TextStyle1_12_2 style;

    public TextHelper1_12_2() {
        this.style = new TextStyle1_12_2();
    }

    @Override public TextString1_12_2 getLiteral(String text) {
        return new TextString1_12_2(this,text);
    }

    @Override public TextStyle1_12_2 getStyle() {
        return this.style;
    }

    @Override public TextTranslation1_12_2 getTranslated(String key, Object ... args) {
        return new TextTranslation1_12_2(this,key,args);
    }
}