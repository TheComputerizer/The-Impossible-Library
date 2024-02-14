package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;

public class TextStringLegacy extends TextLegacy implements TextStringAPI<Style> {

    private final ITextComponent component;
    private final String original;

    public TextStringLegacy(String original) {
        this.original = original;
        this.component = new TextComponentString(original);
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
