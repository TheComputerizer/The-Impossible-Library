package mods.thecomputerizer.theimpossiblelibrary.shared.v19.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.network.chat.Style;

public class TextHelper1_19 implements TextHelperAPI<Style> {

    private final TextStyle1_19 style;

    public TextHelper1_19() {
        this.style = new TextStyle1_19();
    }
    
    @Override public TextString1_19 getLiteral(String text) {
        return new TextString1_19(this, text);
    }

    @Override public TextStyle1_19 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslation1_19(this, key, args);
    }
}