package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.Style;

public class TextHelperForge implements TextHelperAPI<Style> {

    @Override
    public TextStringAPI<Style> getLiteral(String text) {
        return new TextStringForge(text);
    }

    @Override
    public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslationForge(key,args);
    }
}
