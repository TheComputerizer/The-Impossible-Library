package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;

import java.util.Objects;

public class TextString1_18_2 extends Text1_18_2 implements TextStringAPI<Style> {
    
    private final TextHelper1_18_2 helper;
    private final MutableComponent component;
    private final String original;
    
    public TextString1_18_2(TextHelper1_18_2 helper, String original) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = new TextComponent(original);
    }
    
    @Override public String getApplied() {
        return this.component.getString();
    }
    
    @Override public TextHelper1_18_2 getHelper() {
        return this.helper;
    }
    
    @Override public String getOriginal() {
        return this.original;
    }
    
    @Override public MutableComponent getComponent() {
        return this.component;
    }
    
    @Override public TextString1_18_2 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }
    
    @Override public TextString1_18_2 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}