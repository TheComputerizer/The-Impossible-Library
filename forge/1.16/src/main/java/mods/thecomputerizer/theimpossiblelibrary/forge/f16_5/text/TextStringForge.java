package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

public class TextStringForge extends TextForge implements TextStringAPI<Style> {

    private final TextHelperAPI<Style> helper;
    private final IFormattableTextComponent component;
    private final String original;

    public TextStringForge(TextHelperAPI<Style> helper, String original) {
        this.helper = helper;
        this.original = original;
        this.component = new StringTextComponent(original);
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
    public TextStringAPI<Style> setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }

    @Override
    public TextStringAPI<Style> withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}
