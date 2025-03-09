package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.text.TextHelper1_20;

public class TextHelper1_20_4 extends TextHelper1_20 {
    
    @Override public TextStringAPI<?> getLiteral(String text) {
        return new TextString1_20_4(this,text);
    }
}