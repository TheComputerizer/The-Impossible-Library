package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import org.joml.Vector3d;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_TEX_COLOR;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.*;
import static org.lwjgl.opengl.GL11.*;

public class Render1_18_2 extends RenderAPI {
    
    private PoseStack modelView;
    
    public Render1_18_2() {
        super(new GL1_18_2());
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
    
    @Override public void beginBuffer(Object buffer, int mode, Object vertexFormat) {
        if(vertexFormat==POSITION_COLOR) RenderSystem.setShader(GameRenderer::getPositionColorShader);
        else if(vertexFormat==POSITION_TEX_COLOR) RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        ((BufferBuilder)buffer).begin(getBufferMode(mode),(VertexFormat)vertexFormat);
    }
    
    void assertRenderThread() {
        RenderSystem.assertOnRenderThreadOrInit();
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
        int iX = x.intValue();
        int iY = y.intValue();
        int iWidth = width.intValue();
        int iHeight = height.intValue();
        int iMaxWidth = maxWidth.intValue();
        Screen curScreen = Minecraft.getInstance().screen;
        if(Objects.nonNull(curScreen)) {
            List<Component> unwrapped = font.unwrapTooltipComponents(lines);
            curScreen.renderComponentTooltip(unwrapMatrix(),unwrapped,iX,iY);
        }
        else font.renderToolTip(this,lines,iX,iY,iWidth,iHeight,iMaxWidth);
    }

    @Override public void enableAlpha() {
        assertRenderThread();
        GL11.glEnable(GL_ALPHA_TEST);
    }

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

    @Override public void enableTexture() {
        RenderSystem.enableTexture();
    }
    
    @Override public void endBuffer() {
        Tesselator.getInstance().end();
    }
    
    @Override public void endVertex(Object buffer) {
        ((BufferBuilder)buffer).endVertex();
    }
    
    @SuppressWarnings("unchecked")
    @Override public <B> B getBufferBuilder() {
        return (B)Tesselator.getInstance().getBuilder();
    }
    
    @Override public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        return new VertexWrapper1_18_2(getBufferMode(mode),POSITION_COLOR,vertices,3,4);
    }
    
    @Override public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        return new VertexWrapper1_18_2(getBufferMode(mode),POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    private Mode getBufferMode(int mode) {
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
    
    @Override public PoseStack getMatrix() {
        return unwrapMatrix();
    }

    @Override public RenderAPI init(Object context) {
        setMatrix(context);
        return this;
    }
    
    
    public void drawLinesModelView(PoseStack stack, Vector3d ... vectors) {
        PoseStack modelStack = RenderSystem.getModelViewStack();
        modelStack.pushPose();
        modelStack.mulPoseMatrix(stack.last().pose());
        RenderSystem.applyModelViewMatrix();
        // render code is the same
        modelStack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
    
    public void drawLines(PoseStack stack, Vector3d ... vectors) {
        stack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.disableTexture();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(LINE_STRIP,POSITION_COLOR);
        for(Vector3d vector : vectors)
            buffer.vertex(vector.x,vector.y,vector.z).color(1f,1f,1f,1f).endVertex();
        Tesselator.getInstance().end();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        stack.popPose();
    }
    
    @Override public void modelView() {
        if(Objects.nonNull(this.modelView)) {
            this.modelView.popPose();
            RenderSystem.applyModelViewMatrix();
            this.modelView = null;
        } else {
            this.modelView = RenderSystem.getModelViewStack();
            this.modelView.pushPose();
            this.modelView.mulPoseMatrix(getMatrix().last().pose());
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
        return MultiBufferSource.immediate(getBufferBuilder());
    }
    
    @Override public void resetTextureMatrix() {}
    
    @Override public void rotate(float angle, float x, float y, float z) {
        assertRenderThread();
        GL11.glRotatef(angle,x,y,z);
    }
    
    @Override public void scale(float x, float y, float z) {
        if(Objects.nonNull(this.matrix)) getMatrix().scale(x, y, z);
    }

    @Override public void setColor(float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r,g,b,a);
    }

    @Override public void setPosColorShader() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
    }

    @Override public void translate(double x, double y, double z) {
        assertRenderThread();
        GL11.glTranslated(x,y,z);
    }

    @Override public void translate(float x, float y, float z) {
        assertRenderThread();
        GL11.glTranslatef(x,y,z);
    }
    
    @Override public <B> B vertexWithMatrix(B buffer, Object matrix, float x, float y, float z) {
        ((BufferBuilder)buffer).vertex((Matrix4f)matrix,x,y,z);
        return buffer;
    }
    
    @Override public <B> B vertexColor(B buffer, float red, float green, float blue, float alpha) {
        ((BufferBuilder)buffer).color(red,green,blue,alpha);
        return buffer;
    }
}