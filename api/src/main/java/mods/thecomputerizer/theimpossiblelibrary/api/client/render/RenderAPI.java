package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.List;

public interface RenderAPI {

    void drawCenteredString(FontAPI font, String str, int x, int y, int color);
    void drawString(FontAPI font, String str, int left, int top, int color);
    void defaultBlendFunc();
    void disableAlpha();
    void disableBlend();
    void enableAlpha();
    void enableBlend();
    void enableTexture();
    void disableTexture();
    void popMatrix();
    void pushMatrix();
    void renderTooltip(FontAPI font, List<TextAPI<?>> lines, int x, int y);
    void resetTextureMatrix();
    void scale(float x, float y, float z);
    void setColor(float r, float g, float b, float a);
    void setPosColorShader();
}
