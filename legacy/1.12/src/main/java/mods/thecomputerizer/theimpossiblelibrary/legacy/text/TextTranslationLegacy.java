package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;

public class TextTranslationLegacy extends TextLegacy implements TextTranslationAPI<Style> {

    private final TextHelperAPI<Style> helper;
    private final ITextComponent component;
    private final String original;

    public TextTranslationLegacy(TextHelperAPI<Style> helper, String original, Object ... args) {
        this.helper = helper;
        this.original = original;
        this.component = new TextComponentTranslation(original,args);
    }

    @Override
    public String getApplied() {
        return this.component.getFormattedText();
    }

    @Override
    public TextHelperAPI<Style> getHelper() {
        return this.helper;
    }

    @Override
    public ITextComponent getComponent() {
        return this.component;
    }

    @Override
    public String getOriginal() {
        return this.original;
    }

    @Override
    public TextTranslationAPI<Style> setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }

    @Override
    public TextTranslationAPI<Style> withStyle(Style style) {
        return setStyle(((TextStyleLegacy)this.helper.getStyleAPI()).append(this.component.getStyle(),style));
    }
}