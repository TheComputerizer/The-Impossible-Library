package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class TextTranslation1_16_5 extends Text1_16_5 implements TextTranslationAPI<Style> {

    private final TextHelperAPI<Style> helper;
    private final IFormattableTextComponent component;
    private final String original;

    public TextTranslation1_16_5(TextHelperAPI<Style> helper, String original, Object ... args) {
        this.helper = helper;
        this.original = original;
        this.component = new TranslationTextComponent(original,args);
    }

    @Override
    public String getApplied() {
        return this.component.getString();
    }

    @Override
    public TextHelperAPI<Style> getHelper() {
        return this.helper;
    }

    @Override
    public String getOriginal() {
        return this.original;
    }

    @Override
    public IFormattableTextComponent getComponent() {
        return this.component;
    }

    @Override
    public TextTranslationAPI<Style> setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }

    @Override
    public TextTranslationAPI<Style> withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}
