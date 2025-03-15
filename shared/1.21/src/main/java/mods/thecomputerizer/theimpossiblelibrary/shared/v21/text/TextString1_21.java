package mods.thecomputerizer.theimpossiblelibrary.shared.v21.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;

import java.util.Objects;

public class TextString1_21 extends Text1_21 implements TextStringAPI<Style> {
    
    private final TextHelper1_21 helper;
    private final MutableComponent component;
    private final String original;
    
    public TextString1_21(TextHelper1_21 helper, String original) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = MutableComponent.create(new LiteralContents(original));
    }
    
    @Override public String getApplied() {
        return this.component.getString();
    }
    
    @Override public TextHelper1_21 getHelper() {
        return this.helper;
    }
    
    @Override public String getOriginal() {
        return this.original;
    }
    
    @Override public MutableComponent getComponent() {
        return this.component;
    }
    
    @Override public TextString1_21 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }
    
    @Override public TextString1_21 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}