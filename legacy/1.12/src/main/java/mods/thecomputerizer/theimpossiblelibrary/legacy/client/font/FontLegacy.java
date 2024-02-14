package mods.thecomputerizer.theimpossiblelibrary.legacy.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.Objects;

public class FontLegacy implements FontAPI {

    private FontRenderer font;

    private FontRenderer getFont() {
        if(Objects.isNull(this.font)) this.font = Minecraft.getMinecraft().fontRenderer;
        return this.font;
    }

    @Override
    public int getCharWidth(char c) {
        return getFont().getCharWidth(c);
    }

    @Override
    public int getFontHeight() {
        return getFont().FONT_HEIGHT;
    }

    @Override
    public int getStringWidth(String str) {
        return getFont().getStringWidth(str);
    }
}
