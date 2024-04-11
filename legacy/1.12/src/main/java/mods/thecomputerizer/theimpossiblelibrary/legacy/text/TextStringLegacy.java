package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;

public class TextStringLegacy extends TextLegacy implements TextStringAPI<Style> {

    private final TextHelperAPI<Style> helper;
    private final ITextComponent component;
    private final String original;

    public TextStringLegacy(TextHelperAPI<Style> helper, String original) {
        this.helper = helper;
        this.original = original;
        this.component = new TextComponentString(original);
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
    public TextStringAPI<Style> setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }

    @Override
    public TextStringAPI<Style> withStyle(Style style) {
        return setStyle(((TextStyleLegacy)this.helper.getStyleAPI()).append(this.component.getStyle(),style));
    }
}