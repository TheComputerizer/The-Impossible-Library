package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;

import java.util.Objects;

public class TextString1_12_2 extends Text1_12_2 implements TextStringAPI<Style> {

    private final TextHelper1_12_2 helper;
    private final ITextComponent component;
    private final String original;

    public TextString1_12_2(TextHelper1_12_2 helper, String original) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = new TextComponentString(original);
    }

    @Override public String getApplied() {
        return this.component.getFormattedText();
    }

    @Override public TextHelper1_12_2 getHelper() {
        return this.helper;
    }

    @Override public ITextComponent getComponent() {
        return this.component;
    }

    @Override public String getOriginal() {
        return this.original;
    }

    @Override public TextString1_12_2 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }

    @Override public TextString1_12_2 withStyle(Style style) {
        return setStyle(this.helper.getStyle().append(this.component.getStyle(),style));
    }
}