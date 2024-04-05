package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.List;

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
    FontAPI getFont();
    GLAPI getGLAPI();
    MinecraftAPI getMinecraft();
    int getMouseX();
    int getMouseY();
    float getPartialTicks();
    MinecraftWindow getWindow();
    RenderAPI init(Object context);
    void popMatrix();
    void pushMatrix();
    void renderTooltip(FontAPI font, List<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth);
    void resetTextureMatrix();
    void rotate(float angle, float x, float y, float z);
    void scale(float x, float y, float z);
    void setColor(float r, float g, float b, float a);
    /**
     * Unused in 1.12.2
     */
    void setMatrix(Object matrix);
    RenderAPI setMouse(int x, int y);
    RenderAPI setPartialTicks(float partialTicks);
    void setPosColorShader();
    void translate(double x, double y, double z);
    void translate(float x, float y, float z);
}
