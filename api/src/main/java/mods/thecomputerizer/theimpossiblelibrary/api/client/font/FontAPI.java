package mods.thecomputerizer.theimpossiblelibrary.api.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.Collection;

@SuppressWarnings("unused")
public interface FontAPI {

    void draw(RenderAPI renderer, String text, float x, float y, int color);
    void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color);
    int getCharWidth(char c);
    int getFontHeight();
    int getStringWidth(String str);
    void renderToolTip(RenderAPI renderer, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth);
}