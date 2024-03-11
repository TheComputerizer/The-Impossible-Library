package mods.thecomputerizer.theimpossiblelibrary.legacy.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.MinecraftLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class RenderLegacy implements RenderAPI {

    private final GLLegacy gl;
    private int mouseX;
    private int mouseY;
    private float partialTicks;

    public RenderLegacy() {
        this.gl = new GLLegacy();
    }

    @Override
    public void alphaFuncEqual(float alpha) {
        GlStateManager.alphaFunc(GL11.GL_EQUAL,alpha);
    }

    @Override
    public void alphaFuncGreater(float alpha) {
        GlStateManager.alphaFunc(GL11.GL_GREATER,alpha);
    }

    @Override
    public void alphaFuncLesser(float alpha) {
        GlStateManager.alphaFunc(GL11.GL_LESS,alpha);
    }

    @Override
    public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getMinecraft().getTextureManager().bindTexture((ResourceLocation)location.get());
    }

    @Override
    public void defaultBlendFunc() {
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void depthMask(boolean mask) {
        GlStateManager.depthMask(mask);
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
    public void disableCull() {
        GlStateManager.disableCull();
    }

    @Override
    public void disableLighting() {
        GlStateManager.disableLighting();
    }

    @Override
    public void disableTexture() {
        GlStateManager.disableTexture2D();
    }

    @Override
    public void drawCenteredString(FontAPI font, String str, int x, int y, int color) {
        font.drawWithShadow(this,str,(float)(x-font.getStringWidth(str)/2),(float)y,color);
    }

    @Override
    public void drawString(FontAPI font, String str, int left, int top, int color) {
        font.draw(this,str,left,top,color);
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
    public void enableCull() {
        GlStateManager.enableCull();
    }

    @Override
    public void enableLighting() {
        GlStateManager.enableLighting();
    }

    @Override
    public void enableTexture() {
        GlStateManager.enableTexture2D();
    }

    @Override
    public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        return new VertexWrapperLegacy(mode,DefaultVertexFormats.POSITION_COLOR,vertices,3,4);
    }

    @Override
    public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapperLegacy(mode,DefaultVertexFormats.POSITION_TEX_COLOR,vertices,3,2,4);
    }

    @Override
    public FontAPI getFont() {
        return MinecraftLegacy.getInstance().getFont();
    }

    @Override
    public GLAPI getGLAPI() {
        return this.gl;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return MinecraftLegacy.getInstance();
    }

    @Override
    public int getMouseX() {
        return this.mouseX;
    }

    @Override
    public int getMouseY() {
        return this.mouseY;
    }

    @Override
    public float getPartialTicks() {
        return this.partialTicks;
    }

    @Override
    public MinecraftWindow getWindow() {
        return MinecraftLegacy.getInstance().getWindow();
    }

    @Override
    public RenderAPI init(Object context) {
        return this;
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
    public void renderTooltip(FontAPI font, List<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        font.renderToolTip(this,lines,x,y,width,height,maxWidth);
    }

    @Override
    public void resetTextureMatrix() {}

    @Override
    public void rotate(float angle, float x, float y, float z) {
        GlStateManager.rotate(angle,x,y,z);
    }

    @Override
    public void scale(float x, float y, float z) {
        GlStateManager.scale(x,y,z);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        GlStateManager.color(r,g,b,a);
    }

    @Override
    public RenderAPI setMouse(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
        return this;
    }

    @Override
    public RenderAPI setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
        return this;
    }

    @Override
    public void setPosColorShader() {}

    @Override
    public void translate(double x, double y, double z) {
        GlStateManager.translate(x,y,z);
    }

    @Override
    public void translate(float x, float y, float z) {
        GlStateManager.translate(x,y,z);
    }
}
