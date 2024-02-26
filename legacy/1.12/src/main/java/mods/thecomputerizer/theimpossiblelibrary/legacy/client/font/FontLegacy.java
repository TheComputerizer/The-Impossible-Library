package mods.thecomputerizer.theimpossiblelibrary.legacy.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FontLegacy implements FontAPI {

    private FontRenderer font;

    @Override
    public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().drawString(text,x,y,color,false);
    }

    @Override
    public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        getFont().drawStringWithShadow(text,x,y,color);
    }

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

    @Override
    public void renderToolTip(RenderAPI renderer, List<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<String> strings = new ArrayList<>();
        for(TextAPI<?> text : lines) strings.add(text.getApplied());
        GuiUtils.drawHoveringText(strings,x,y,width,height,maxWidth,getFont());
    }
}
