package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.Collection;

import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LESS;

@SuppressWarnings("deprecation") @Getter
public abstract class Render1_16_5 implements RenderAPI {

    private final GL1_16_5 gl;
    @Setter private int mouseX;
    @Setter private int mouseY;

    protected Render1_16_5(GL1_16_5 gl) {
        this.gl = gl;
    }

    @Override public void alphaFuncEqual(float alpha) {
        RenderSystem.alphaFunc(GL_EQUAL,alpha);
    }

    @Override public void alphaFuncGreater(float alpha) {
        RenderSystem.alphaFunc(GL_GREATER,alpha);
    }

    @Override public void alphaFuncLesser(float alpha) {
        RenderSystem.alphaFunc(GL_LESS,alpha);
    }

    @Override public void defaultBlendFunc() {
        RenderSystem.defaultBlendFunc();
    }

    @Override public void depthMask(boolean mask) {
        RenderSystem.depthMask(mask);
    }

    @Override public void disableAlpha() {
        RenderSystem.disableAlphaTest();
    }

    @Override public void disableBlend() {
        RenderSystem.disableBlend();
    }

    @Override public void disableCull() {
        RenderSystem.disableCull();
    }

    @Override public void disableLighting() {
        RenderSystem.disableLighting();
    }

    @Override public void disableTexture() {
        RenderSystem.disableTexture();
    }

    @Override public void drawCenteredString(FontAPI font, String str, Number x, Number y, int color) {
        font.drawWithShadow(this,str,x.floatValue()-font.getStringWidth(str)/2f,y.floatValue(),color);
    }

    @Override public void drawString(FontAPI font, String str, Number left, Number top, int color) {
        font.draw(this,str,left.intValue(),top.intValue(),color);
    }
    
    @Override public void drawTooltip(FontAPI font, Collection<TextAPI<?>> lines, Number x, Number y, Number width,
            Number height, Number maxWidth) {
        font.renderToolTip(this,lines,x.intValue(),y.intValue(),width.intValue(),height.intValue(),maxWidth.intValue());
    }

    @Override public void enableAlpha() {
        RenderSystem.enableAlphaTest();
    }

    @Override public void enableBlend() {
        RenderSystem.enableBlend();
    }

    @Override public void enableCull() {
        RenderSystem.enableCull();
    }

    @Override public void enableLighting() {
        RenderSystem.enableLighting();
    }

    @Override public void enableTexture() {
        RenderSystem.enableTexture();
    }

    @Override public GLAPI getGLAPI() {
        return this.gl;
    }

    @Override public RenderAPI init(Object context) {
        setMatrix(context);
        return this;
    }

    @Override public void resetTextureMatrix() {}
    
    @Override public void rotate(float angle, float x, float y, float z) {
        RenderSystem.rotatef(angle,x,y,z);
    }

    @Override public void setColor(float r, float g, float b, float a) {
        RenderSystem.color4f(r,g,b,a);
    }

    @Override public void setPosColorShader() {}

    @Override public void translate(double x, double y, double z) {
        RenderSystem.translated(x,y,z);
    }

    @Override public void translate(float x, float y, float z) {
        RenderSystem.translatef(x,y,z);
    }
}
