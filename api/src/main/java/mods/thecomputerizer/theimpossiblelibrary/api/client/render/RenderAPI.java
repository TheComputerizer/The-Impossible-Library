package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.Collection;

@SuppressWarnings("unused")
public interface RenderAPI {

    void alphaFuncEqual(float alpha);
    void alphaFuncGreater(float alpha);
    void alphaFuncLesser(float alpha);
    void bindTexture(ResourceLocationAPI<?> location);
    void defaultBlendFunc();
    void depthMask(boolean mask);
    void disableAlpha();
    void disableBlend();
    void disableCull();
    void disableLighting();
    void disableTexture();
    void drawCenteredString(FontAPI font, String str, int x, int y, int color);
    void drawString(FontAPI font, String str, int left, int top, int color);
    void drawTooltip(FontAPI font, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth);
    void enableAlpha();
    void enableBlend();
    void enableCull();
    void enableLighting();
    void enableTexture();
    /**
     * POSITION_COLOR
     */
    VertexWrapper getBufferBuilderPC(int mode, int vertices);
    /**
     * POSITION_TEX_COLOR
     */
    VertexWrapper getBufferBuilderPTC(int mode, int vertices);
    GLAPI getGLAPI();
    RenderAPI init(Object context);
    void popMatrix();
    void pushMatrix();
    void resetTextureMatrix();
    void rotate(float angle, float x, float y, float z);
    void scale(float x, float y, float z);
    void setColor(float r, float g, float b, float a);
    /**
     * Unused in 1.12.2
     */
    void setMatrix(Object matrix);
    void setPosColorShader();
    void translate(double x, double y, double z);
    void translate(float x, float y, float z);
}
