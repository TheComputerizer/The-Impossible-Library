package mods.thecomputerizer.theimpossiblelibrary.legacy.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;

public abstract class TextLegacy implements TextAPI<Style> {

    public abstract ITextComponent getComponent();
}