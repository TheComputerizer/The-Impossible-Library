package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Objects;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX_COLOR;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LESS;

@Getter
public class Render1_16_5 implements RenderAPI {

    private final GL1_16_5 gl;
    private MatrixStack matrix;
    @Setter private int mouseX;
    @Setter private int mouseY;

    public Render1_16_5() {
        this.gl = new GL1_16_5();
    }


    @SuppressWarnings("deprecation")
    @Override
    public void alphaFuncEqual(float alpha) {
        RenderSystem.alphaFunc(GL_EQUAL,alpha);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void alphaFuncGreater(float alpha) {
        RenderSystem.alphaFunc(GL_GREATER,alpha);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void alphaFuncLesser(float alpha) {
        RenderSystem.alphaFunc(GL_LESS,alpha);
    }

    @Override
    public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getInstance().getTextureManager().bind((ResourceLocation)location.getInstance());
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
    public void drawCenteredString(FontAPI font, String str, Number x, Number y, int color) {
        font.drawWithShadow(this,str,x.floatValue()-font.getStringWidth(str)/2f,y.floatValue(),color);
    }

    @Override
    public void drawString(FontAPI font, String str, Number left, Number top, int color) {
        font.draw(this,str,left.intValue(),top.intValue(),color);
    }
    
    @Override
    public void drawTooltip(FontAPI font, Collection<TextAPI<?>> lines, Number x, Number y, Number width,
            Number height, Number maxWidth) {
        font.renderToolTip(this,lines,x.intValue(),y.intValue(),width.intValue(),height.intValue(),maxWidth.intValue());
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
        return new VertexWrapper1_16_5(mode,POSITION_COLOR,vertices,3,4);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapper1_16_5(mode,POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    @Override public int getDirectMouseX() { //TODO
        return 0;
    }
    
    @Override public int getDirectMouseY() { //TODO
        return 0;
    }

    @Override
    public GLAPI getGLAPI() {
        return this.gl;
    }

    @Override
    public RenderAPI init(Object context) {
        setMatrix(context);
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
