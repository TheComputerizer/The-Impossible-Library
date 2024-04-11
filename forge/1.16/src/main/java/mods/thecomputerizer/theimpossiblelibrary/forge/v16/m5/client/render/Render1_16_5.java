package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Minecraft1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Objects;

@Getter
public class Render1_16_5 implements RenderAPI {

    private final GL1_16_5 gl;
    private MatrixStack matrix;
    @Setter private int mouseX;
    @Setter private int mouseY;
    private float partialTicks;

    public Render1_16_5() {
        this.gl = new GL1_16_5();
    }


    @SuppressWarnings("deprecation")
    @Override
    public void alphaFuncEqual(float alpha) {
        RenderSystem.alphaFunc(GL11.GL_EQUAL,alpha);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void alphaFuncGreater(float alpha) {
        RenderSystem.alphaFunc(GL11.GL_GREATER,alpha);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void alphaFuncLesser(float alpha) {
        RenderSystem.alphaFunc(GL11.GL_LESS,alpha);
    }

    @Override
    public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getInstance().getTextureManager().bind((ResourceLocation)location.get());
    }

    @Override
    public void defaultBlendFunc() {
        RenderSystem.defaultBlendFunc();
    }

    @Override
    public void depthMask(boolean mask) {
        RenderSystem.depthMask(mask);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void disableAlpha() {
        RenderSystem.disableAlphaTest();
    }

    @Override
    public void disableBlend() {
        RenderSystem.disableBlend();
    }

    @Override
    public void disableCull() {
        RenderSystem.disableCull();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void disableLighting() {
        RenderSystem.disableLighting();
    }

    @Override
    public void disableTexture() {
        RenderSystem.disableTexture();
    }

    @Override
    public void drawCenteredString(FontAPI font, String str, int x, int y, int color) {
        font.drawWithShadow(this,str,(float)(x-font.getStringWidth(str)/2),(float)y,color);
    }

    @Override
    public void drawString(FontAPI font, String str, int left, int top, int color) {
        font.draw(this,str,left,top,color);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void enableAlpha() {
        RenderSystem.enableAlphaTest();
    }

    @Override
    public void enableBlend() {
        RenderSystem.enableBlend();
    }

    @Override
    public void enableCull() {
        RenderSystem.enableCull();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void enableLighting() {
        RenderSystem.enableLighting();
    }

    @Override
    public void enableTexture() {
        RenderSystem.enableTexture();
    }

    @Override
    public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        return new VertexWrapper1_16_5(mode,DefaultVertexFormats.POSITION_COLOR,vertices,3,4);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapper1_16_5(mode,DefaultVertexFormats.POSITION_TEX_COLOR,vertices,3,2,4);
    }

    @Override
    public FontAPI getFont() {
        return Minecraft1_16_5.getInstance().getFont();
    }

    @Override
    public GLAPI getGLAPI() {
        return this.gl;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        return Minecraft1_16_5.getInstance();
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
        return Minecraft1_16_5.getInstance().getWindow();
    }

    @Override
    public RenderAPI init(Object context) {
        setMatrix((MatrixStack)context);
        return this;
    }

    @Override
    public void popMatrix() {
        this.matrix.popPose();
    }

    @Override
    public void pushMatrix() {
        this.matrix.pushPose();
    }

    @Override
    public void renderTooltip(FontAPI font, List<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        font.renderToolTip(this,lines,x,y,width,height,maxWidth);
    }

    @Override
    public void resetTextureMatrix() {}

    @SuppressWarnings("deprecation")
    @Override
    public void rotate(float angle, float x, float y, float z) {
        RenderSystem.rotatef(angle,x,y,z);
    }

    @Override
    public void scale(float x, float y, float z) {
        if(Objects.nonNull(this.matrix)) this.matrix.scale(x,y,z);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setColor(float r, float g, float b, float a) {
        RenderSystem.color4f(r,g,b,a);
    }

    @Override
    public void setMatrix(Object matrix) {
        this.matrix = matrix instanceof MatrixStack ? (MatrixStack)matrix : null;
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

    @SuppressWarnings("deprecation")
    @Override
    public void translate(double x, double y, double z) {
        RenderSystem.translated(x,y,z);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void translate(float x, float y, float z) {
        RenderSystem.translatef(x,y,z);
    }
}
