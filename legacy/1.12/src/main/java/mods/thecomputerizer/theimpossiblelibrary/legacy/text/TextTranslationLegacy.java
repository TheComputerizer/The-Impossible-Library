package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;

public class TextTranslationLegacy extends TextLegacy implements TextTranslationAPI<Style> {

    private final ITextComponent component;
    private final String original;

    public TextTranslationLegacy(String original, Object args) {
        this.original = original;
        this.component = new TextComponentTranslation(original,args);
    }
    @Override
    public void applyStyle(Style style) {
        this.component.setStyle(style);
    }

    @Override
    public String getApplied() {
        return this.component.getFormattedText();
    }

    @Override
    public String getOriginal() {
        return this.original;
    }
}
