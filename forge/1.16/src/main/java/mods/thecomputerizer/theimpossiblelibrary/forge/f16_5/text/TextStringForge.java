package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class TextStringForge extends TextForge implements TextStringAPI<Style> {

    private final IFormattableTextComponent component;
    private final String original;

    public TextStringForge(String original) {
        this.original = original;
        this.component = new StringTextComponent(original);
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
