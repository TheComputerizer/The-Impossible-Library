package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.util.Collection;
import java.util.Objects;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX_COLOR;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LESS;

public class Render1_18_2 extends RenderAPI {
    
    public Render1_18_2() {
        super(new GL1_18_2());
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
    
    @Override public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getInstance().getTextureManager().bindForSetup(location.unwrap());
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

    @Override public void drawCenteredString(FontAPI<?> font, String str, Number x, Number y, int color) {
        font.drawWithShadow(this,str,x.floatValue()-font.getStringWidth(str)/2f,y.floatValue(),color);
    }

    @Override public void drawString(FontAPI<?> font, String str, Number left, Number top, int color) {
        font.draw(this,str,left.intValue(),top.intValue(),color);
    }
    
    @Override public void drawTooltip(FontAPI<?> font, Collection<TextAPI<?>> lines, Number x, Number y, Number width,
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
    
    @Override public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        return new VertexWrapper1_18_2(getBufferMode(mode),POSITION_COLOR,vertices,3,4);
    }
    
    @Override public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapper1_18_2(getBufferMode(mode),POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    private Mode getBufferMode(int mode) {
        Mode m = QUADS;
        for(Mode potential : Mode.values()) {
            if(potential.asGLMode==mode) {
                m = potential;
                break;
            }
        }
        return m;
    }
    
    @Override public double getDirectMouseX() {
        return Minecraft.getInstance().mouseHandler.xpos();
    }
    
    @Override public double getDirectMouseY() {
        return Minecraft.getInstance().mouseHandler.ypos();
    }
    
    @Override public Font getFont() {
        return unwrapFont();
    }

    @Override public GLAPI getGLAPI() {
        return this.gl;
    }
    
    @Override public PoseStack getMatrix() {
        return unwrapMatrix();
    }

    @Override public RenderAPI init(Object context) {
        setMatrix(context);
        return this;
    }
    
    @Override public void popMatrix() {
        getMatrix().popPose();
    }
    
    @Override public void pushMatrix() {
        getMatrix().pushPose();
    }

    @Override public void resetTextureMatrix() {}
    
    @Override public void rotate(float angle, float x, float y, float z) {
        RenderSystem.rotatef(angle,x,y,z);
    }
    
    @Override public void scale(float x, float y, float z) {
        if(Objects.nonNull(this.matrix)) getMatrix().scale(x, y, z);
    }

    @Override public void setColor(float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r,g,b,a);
    }

    @Override public void setPosColorShader() {}

    @Override public void translate(double x, double y, double z) {
        RenderSystem.translated(x,y,z);
    }

    @Override public void translate(float x, float y, float z) {
        RenderSystem.translatef(x,y,z);
    }
}