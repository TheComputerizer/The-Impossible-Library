package mods.thecomputerizer.theimpossiblelibrary.shared.v21.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.network.chat.Style;

public class TextHelper1_21 implements TextHelperAPI<Style> {

    protected final TextStyle1_21 style;

    public TextHelper1_21() {
        this.style = new TextStyle1_21();
    }
    
    @Override public TextStringAPI<?> getLiteral(String text) {
        return new TextString1_21(this, text);
    }

    @Override public TextStyle1_21 getStyle() {
        return this.style;
    }
    
    @Override public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslation1_21(this,key,args);
    }
}