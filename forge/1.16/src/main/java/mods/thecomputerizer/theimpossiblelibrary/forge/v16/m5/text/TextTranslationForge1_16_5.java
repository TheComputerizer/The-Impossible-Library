package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Objects;

public class TextTranslationForge1_16_5 extends TextForge1_16_5 implements TextTranslationAPI<Style> {
    
    private final TextHelperForge1_16_5 helper;
    private final IFormattableTextComponent component;
    private final String original;
    
    public TextTranslationForge1_16_5(TextHelperForge1_16_5 helper, String original, Object ... args) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = new TranslationTextComponent(original, Objects.nonNull(args) ? args : new Object[]{});
    }
    
    @Override public String getApplied() {
        return this.component.getString();
    }
    
    @Override public TextHelperForge1_16_5 getHelper() {
        return this.helper;
    }
    
    @Override public String getOriginal() {
        return this.original;
    }
    
    @Override public IFormattableTextComponent getComponent() {
        return this.component;
    }
    
    @Override public TextTranslationForge1_16_5 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }
    
    @Override public TextTranslationForge1_16_5 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}
