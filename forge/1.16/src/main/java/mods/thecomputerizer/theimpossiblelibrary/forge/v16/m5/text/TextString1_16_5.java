package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

import java.util.Objects;

public class TextString1_16_5 extends Text1_16_5 implements TextStringAPI<Style> {

    private final TextHelper1_16_5 helper;
    private final IFormattableTextComponent component;
    private final String original;

    public TextString1_16_5(TextHelper1_16_5 helper, String original) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = new StringTextComponent(original);
    }

    @Override public String getApplied() {
        return this.component.getString();
    }

    @Override public TextHelper1_16_5 getHelper() {
        return this.helper;
    }

    @Override public String getOriginal() {
        return this.original;
    }

    @Override public IFormattableTextComponent getComponent() {
        return this.component;
    }

    @Override public TextString1_16_5 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }

    @Override public TextString1_16_5 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}