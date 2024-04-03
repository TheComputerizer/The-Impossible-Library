package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.Style;

public class TextHelperLegacy implements TextHelperAPI<Style> {

    @Override
    public TextStringAPI<Style> getLiteral(String text) {
        return new TextStringLegacy(text);
    }

    @Override
    public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslationLegacy(key,args);
    }
}
