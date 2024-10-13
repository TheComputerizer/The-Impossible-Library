package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Objects;

public class TextTranslation1_18_2 extends Text1_18_2 implements TextTranslationAPI<Style> {
    
    private final TextHelper1_18_2 helper;
    private final MutableComponent component;
    private final String original;
    
    public TextTranslation1_18_2(TextHelper1_18_2 helper, String original, Object ... args) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = new TranslatableComponent(original,Objects.nonNull(args) ? args : new Object[]{});
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
    
    @Override public TextTranslation1_18_2 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }
    
    @Override public TextTranslation1_18_2 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}