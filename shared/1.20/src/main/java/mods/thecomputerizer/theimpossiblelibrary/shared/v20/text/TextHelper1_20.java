package mods.thecomputerizer.theimpossiblelibrary.shared.v20.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.network.chat.Style;

public class TextHelper1_20 implements TextHelperAPI<Style> {

    protected final TextStyle1_20 style;

    public TextHelper1_20() {
        this.style = new TextStyle1_20();
    }
    
    @Override public TextStringAPI<?> getLiteral(String text) {
        return new TextString1_20(this, text);
    }

    @Override public TextStyle1_20 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslation1_20(this,key,args);
    }
}