package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStyleAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.Style;

public class TextHelperForge implements TextHelperAPI<Style> {

    private final TextStyleAPI<Style> style;

    public TextHelperForge() {
        this.style = new TextStyleForge();
    }

    @Override
    public TextStringAPI<Style> getLiteral(String text) {
        return new TextStringForge(this,text);
    }

    @Override
    public TextStyleAPI<Style> getStyleAPI() {
        return this.style;
    }

    @Override
    public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslationForge(this,key,args);
    }
}