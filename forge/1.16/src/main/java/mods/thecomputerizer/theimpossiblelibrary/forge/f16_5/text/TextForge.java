package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.Style;

public abstract class TextForge implements TextAPI<Style> {

    public abstract IFormattableTextComponent get();
}