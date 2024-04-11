package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStyleAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.Style;

public class TextHelper1_16_5 implements TextHelperAPI<Style> {

    private final TextStyleAPI<Style> style;

    public TextHelper1_16_5() {
        this.style = new TextStyle1_16_5();
    }

    @Override
    public TextStringAPI<Style> getLiteral(String text) {
        return new TextString1_16_5(this,text);
    }

    @Override
    public TextStyleAPI<Style> getStyleAPI() {
        return this.style;
    }

    @Override
    public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslation1_16_5(this,key,args);
    }
}