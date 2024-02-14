package mods.thecomputerizer.theimpossiblelibrary.legacy.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.renderer.GlStateManager;

import java.util.List;

public class RenderLegacy implements RenderAPI {

    @Override
    public void drawCenteredString(FontAPI font, String str, int x, int y, int color) {

    }

    @Override
    public void drawString(FontAPI font, String str, int left, int top, int color) {

    }

    @Override
    public void defaultBlendFunc() {
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void disableAlpha() {
        GlStateManager.disableAlpha();
    }

    @Override
    public void disableBlend() {
        GlStateManager.disableBlend();
    }

    @Override
    public void enableAlpha() {
        GlStateManager.enableAlpha();
    }

    @Override
    public void enableBlend() {
        GlStateManager.enableBlend();
    }

    @Override
    public void enableTexture() {
        GlStateManager.enableTexture2D();
    }

    @Override
    public void disableTexture() {
        GlStateManager.disableTexture2D();
    }

    @Override
    public void popMatrix() {
        GlStateManager.popMatrix();
    }

    @Override
    public void pushMatrix() {
        GlStateManager.pushMatrix();
    }

    @Override
    public void renderTooltip(FontAPI font, List<TextAPI<?>> lines, int x, int y) {

    }

    @Override
    public void resetTextureMatrix() {}

    @Override
    public void scale(float x, float y, float z) {
        GlStateManager.scale(x,y,z);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        GlStateManager.color(r,g,b,a);
    }

    @Override
    public void setPosColorShader() {}
}
