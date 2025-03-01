package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.text.TextHelper1_19;
import net.minecraft.network.chat.Style;

public class TextHelper1_19_4 extends TextHelper1_19 {
    
    @Override public TextTranslationAPI<Style> getTranslated(String key, Object... args) {
        return new TextTranslation1_19_4(this,key,args);
    }
}