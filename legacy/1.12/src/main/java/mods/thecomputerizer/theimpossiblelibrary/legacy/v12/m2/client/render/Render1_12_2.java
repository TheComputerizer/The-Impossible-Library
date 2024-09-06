package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

import java.util.Collection;

import static net.minecraft.client.renderer.GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA;
import static net.minecraft.client.renderer.GlStateManager.SourceFactor.SRC_ALPHA;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX_COLOR;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LESS;

public class Render1_12_2 implements RenderAPI {

    private final GL1_12_2 gl;

    public Render1_12_2() {
        this.gl = new GL1_12_2();
    }

    @Override public void alphaFuncEqual(float alpha) {
        GlStateManager.alphaFunc(GL_EQUAL,alpha);
    }

    @Override public void alphaFuncGreater(float alpha) {
        GlStateManager.alphaFunc(GL_GREATER,alpha);
    }

    @Override public void alphaFuncLesser(float alpha) {
        GlStateManager.alphaFunc(GL_LESS,alpha);
    }

    @Override public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(location.unwrap());
    }

    @Override public void defaultBlendFunc() {
        GlStateManager.blendFunc(SRC_ALPHA,ONE_MINUS_SRC_ALPHA);
    }

    @Override public void depthMask(boolean mask) {
        GlStateManager.depthMask(mask);
    }

    @Override public void disableAlpha() {
        GlStateManager.disableAlpha();
    }

    @Override public void disableBlend() {
        GlStateManager.disableBlend();
    }

    @Override public void disableCull() {
        GlStateManager.disableCull();
    }

    @Override public void disableLighting() {
        GlStateManager.disableLighting();
    }

    @Override public void disableTexture() {
        GlStateManager.disableTexture2D();
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
        GlStateManager.enableAlpha();
    }

    @Override public void enableBlend() {
        GlStateManager.enableBlend();
    }

    @Override public void enableCull() {
        GlStateManager.enableCull();
    }

    @Override public void enableLighting() {
        GlStateManager.enableLighting();
    }

    @Override public void enableTexture() {
        GlStateManager.enableTexture2D();
    }

    @Override public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        return new VertexWrapper1_12_2(mode,POSITION_COLOR,vertices,3,4);
    }

    @Override public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapper1_12_2(mode,POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    @Override public double getDirectMouseX() {
        return Mouse.getX();
    }
    
    @Override public double getDirectMouseY() {
        return Mouse.getY();
    }
    
    @Override public GLAPI getGLAPI() {
        return this.gl;
    }

    @Override public RenderAPI init(Object context) {
        return this;
    }

    @Override public void popMatrix() {
        GlStateManager.popMatrix();
    }

    @Override public void pushMatrix() {
        GlStateManager.pushMatrix();
    }

    @Override public void resetTextureMatrix() {}

    @Override public void rotate(float angle, float x, float y, float z) {
        GlStateManager.rotate(angle,x,y,z);
    }

    @Override public void scale(float x, float y, float z) {
        GlStateManager.scale(x,y,z);
    }

    @Override public void setColor(float r, float g, float b, float a) {
        GlStateManager.color(r,g,b,a);
    }

    @Override public void setMatrix(Object matrix) {}

    @Override public void setPosColorShader() {}

    @Override public void translate(double x, double y, double z) {
        GlStateManager.translate(x,y,z);
    }

    @Override public void translate(float x, float y, float z) {
        GlStateManager.translate(x,y,z);
    }
}
