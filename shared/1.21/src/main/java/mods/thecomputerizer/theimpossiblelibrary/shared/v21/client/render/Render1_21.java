package mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.mojang.blaze3d.platform.GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA;
import static com.mojang.blaze3d.platform.GlStateManager.SourceFactor.ONE;
import static com.mojang.blaze3d.platform.GlStateManager.SourceFactor.SRC_ALPHA;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX_COLOR;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.*;
import static org.lwjgl.opengl.GL11.*;

public class Render1_21 extends RenderAPI {
    
    protected Matrix4fStack modelView;
    
    public Render1_21() {
        super(new GL1_21());
    }

    @Override public void alphaFuncEqual(float alpha) {
        assertRenderThread();
        GL11.glAlphaFunc(GL_EQUAL,alpha);
    }

    @Override public void alphaFuncGreater(float alpha) {
        assertRenderThread();
        GL11.glAlphaFunc(GL_GREATER,alpha);
    }

    @Override public void alphaFuncLesser(float alpha) {
        assertRenderThread();
        GL11.glAlphaFunc(GL_LESS,alpha);
    }
    
    void assertRenderThread() {
        RenderSystem.assertOnRenderThreadOrInit();
    }
    
    @Override public Object beginBuffer(Object tesselator, int mode, Object vertexFormat) {
        if(vertexFormat==POSITION_COLOR) RenderSystem.setShader(GameRenderer::getPositionColorShader);
        else if(vertexFormat==POSITION_TEX_COLOR) RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        return ((Tesselator)tesselator).begin(getBufferMode(mode),(VertexFormat)vertexFormat);
    }
    
    @Override public void bindTexture(ResourceLocationAPI<?> location) {
        RenderSystem.setShaderTexture(0,location.unwrap());
    }
    
    @Override public void blendTranslucent() {
        RenderSystem.blendFuncSeparate(SRC_ALPHA,ONE_MINUS_SRC_ALPHA,ONE,ONE_MINUS_SRC_ALPHA);
    }

    @Override public void defaultBlendFunc() {
        RenderSystem.defaultBlendFunc();
    }

    @Override public void depthMask(boolean mask) {
        RenderSystem.depthMask(mask);
    }

    @Override public void disableAlpha() {
        assertRenderThread();
        GL11.glDisable(GL_ALPHA_TEST);
    }

    @Override public void disableBlend() {
        RenderSystem.disableBlend();
    }

    @Override public void disableCull() {
        RenderSystem.disableCull();
    }

    @Override public void disableLighting() {
        assertRenderThread();
        GL11.glDisable(GL_LIGHTING);
    }

    @Override public void disableTexture() {}

    @Override public void drawCenteredString(FontAPI<?> font, String str, Number x, Number y, int color) {
        setColor(ColorHelper.decode(color));
        font.drawWithShadow(this,str,x.floatValue()-font.getStringWidth(str)/2f,y.floatValue(),color);
    }

    @Override public void drawString(FontAPI<?> font, String str, Number left, Number top, int color) {
        setColor(ColorHelper.decode(color));
        font.draw(this,str,left.intValue(),top.intValue(),color);
    }
    
    @Override public void drawTooltip(FontAPI<?> font, Collection<TextAPI<?>> lines, Number x, Number y, Number width,
            Number height, Number maxWidth) {
        int iX = x.intValue();
        int iY = y.intValue();
        int iWidth = width.intValue();
        int iHeight = height.intValue();
        int iMaxWidth = maxWidth.intValue();
        Screen curScreen = Minecraft.getInstance().screen;
        if(Objects.nonNull(curScreen)) {
            List<Component> unwrapped = font.unwrapTooltipComponents(lines);
            GuiGraphics graphics = getGraphics();
            if(Objects.nonNull(graphics)) graphics.renderComponentTooltip(font.unwrap(),unwrapped,iX,iY);
        }
        else font.renderToolTip(this,lines,iX,iY,iWidth,iHeight,iMaxWidth);
    }

    @Override public void enableAlpha() {}

    @Override public void enableBlend() {
        RenderSystem.enableBlend();
    }

    @Override public void enableCull() {
        RenderSystem.enableCull();
    }

    @Override public void enableLighting() {
        assertRenderThread();
        GL11.glEnable(GL_LIGHTING);
    }
    
    @Override public void enableTexture() {}
    
    @Override public void endBuffer() {} //Not needed in 1.21+
    
    @Override public void endVertex(Object buffer) {} //Not needed in 1.21+
    
    @Override public <B> B getBufferBuilder() {
        return null; //Doesn't need to be called in 1.21+
    }
    
    @Override public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        return new VertexWrapper1_21(getBufferMode(mode),POSITION_COLOR,vertices,3,4);
    }
    
    @Override public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        return new VertexWrapper1_21(getBufferMode(mode),POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    protected Mode getBufferMode(int mode) {
        return switch(mode) {
            case GL_LINE -> LINES;
            case GL_LINE_LOOP, GL_LINE_STRIP -> LINE_STRIP;
            case GL_TRIANGLES -> TRIANGLES;
            case GL_TRIANGLE_FAN -> TRIANGLE_FAN;
            case GL_TRIANGLE_STRIP -> TRIANGLE_STRIP;
            default -> QUADS;
        };
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
    
    public @Nullable GuiGraphics getGraphics() {
        return this.matrix instanceof GuiGraphics ? (GuiGraphics)this.matrix : null;
    }
    
    @Override public PoseStack getMatrix() {
        return this.matrix instanceof GuiGraphics ? ((GuiGraphics)this.matrix).pose() : unwrapMatrix();
    }

    @Override public RenderAPI init(Object context) {
        setMatrix(context);
        return this;
    }
    
    @Override public void modelView() {
        if(Objects.nonNull(this.modelView)) {
            this.modelView.popMatrix();
            RenderSystem.applyModelViewMatrix();
            this.modelView = null;
        } else {
            this.modelView = RenderSystem.getModelViewStack();
            this.modelView.pushMatrix().mul(RenderSystem.getProjectionMatrix());
            RenderSystem.applyModelViewMatrix();
        }
    }
    
    @Override public void popMatrix() {
        getMatrix().popPose();
    }
    
    @Override public void pushMatrix() {
        getMatrix().pushPose();
    }
    
    @Override public Object renderSourceImmediate() {
        return null; //Doesn't need to be called in 1.21+
    }
    
    @Override public void resetTextureMatrix() {
        RenderSystem.resetTextureMatrix();
    }
    
    @Override public void rotate(float angle, float x, float y, float z) {
        assertRenderThread();
        GL11.glRotatef(angle,x,y,z);
    }
    
    @Override public void scale(float x, float y, float z) {
        PoseStack stack = getMatrix();
        if(Objects.isNull(stack)) TILRef.logError("Tried to scale PoseStack without setting it first");
        else stack.scale(x,y,z);
    }

    @Override public void setColor(float r, float g, float b, float a) {
        GuiGraphics graphics = getGraphics();
        if(Objects.nonNull(graphics)) graphics.setColor(r,g,b,a);
        else RenderSystem.setShaderColor(r,g,b,a);
    }

    @Override public void setPosColorShader() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
    }

    @Override public void translate(double x, double y, double z) {
        PoseStack stack = getMatrix();
        if(Objects.isNull(stack)) TILRef.logError("Tried to translate PoseStack without setting it first");
        else stack.translate(x,y,z);
    }

    @Override public void translate(float x, float y, float z) {
        PoseStack stack = getMatrix();
        if(Objects.isNull(stack)) TILRef.logError("Tried to translate PoseStack without setting it first");
        else stack.translate(x,y,z);
    }
    
    @Override public <B> B vertexWithMatrix(B buffer, Object matrix, float x, float y, float z) {
        ((BufferBuilder)buffer).addVertex((Matrix4f)matrix, x, y, z);
        return buffer;
    }
    
    @Override public <B> B vertexColor(B buffer, float red, float green, float blue, float alpha) {
        ((BufferBuilder)buffer).setColor(red,green,blue,alpha);
        return buffer;
    }
}