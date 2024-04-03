package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

public class TextTranslationForge extends TextForge implements TextTranslationAPI<Style> {

    private final IFormattableTextComponent component;
    private final String original;

    public TextTranslationForge(String original, Object ... args) {
        this.original = original;
        this.component = new TranslationTextComponent(original,args);
    }
    @Override
    public void applyStyle(Style style) {
        this.component.setStyle(style);
    }

    @Override
    public String getApplied() {
        return this.component.getString();
    }

    @Override
    public String getOriginal() {
        return this.original;
    }

    @Override
    public IFormattableTextComponent get() {
        return this.component;
    }
}
