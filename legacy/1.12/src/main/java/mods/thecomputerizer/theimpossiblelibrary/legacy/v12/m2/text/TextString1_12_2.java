package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;

public class TextString1_12_2 extends Text1_12_2 implements TextStringAPI<Style> {

    private final TextHelperAPI<Style> helper;
    private final ITextComponent component;
    private final String original;

    public TextString1_12_2(TextHelperAPI<Style> helper, String original) {
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
        return setStyle(((TextStyle1_12_2)this.helper.getStyleAPI()).append(this.component.getStyle(),style));
    }
}