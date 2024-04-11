package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStyleAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.Style;

public class TextHelperLegacy implements TextHelperAPI<Style> {

    private final TextStyleAPI<Style> style;

    public TextHelperLegacy() {
        this.style = new TextStyleLegacy();
    }

    @Override
    public TextStringAPI<Style> getLiteral(String text) {
        return new TextStringLegacy(this,text);
    }

    @Override
    public TextStyleAPI<Style> getStyleAPI() {
        return this.style;
    }

    @Override
    public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslationLegacy(this,key,args);
    }
}