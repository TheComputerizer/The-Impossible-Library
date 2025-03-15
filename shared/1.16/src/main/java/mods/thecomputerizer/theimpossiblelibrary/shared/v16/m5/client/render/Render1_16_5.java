package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

import java.lang.invoke.MethodHandle;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.mojang.blaze3d.platform.GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA;
import static com.mojang.blaze3d.platform.GlStateManager.SourceFactor.ONE;
import static com.mojang.blaze3d.platform.GlStateManager.SourceFactor.SRC_ALPHA;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LESS;

public class Render1_16_5 extends RenderAPI {
    
    private static MethodHandle renderTypeImmediate;
    
    private static Object renderTypeImmediate(Object buffer) {
        if(Objects.isNull(renderTypeImmediate)) {
            String methodName = DEV ? "immediate" : (CoreAPI.isForge() ? "func_228455_a_" : "method_22991");
            renderTypeImmediate = Methods.findDirectHandle(IRenderTypeBuffer.class,methodName,BufferBuilder.class);
        }
        try {
            return renderTypeImmediate.invoke(buffer);
        } catch(Throwable t) {
            TILRef.logError("renderTypeImmediate failed to invoke",t);
        }
        return null;
    }
    
    public Render1_16_5() {
        super(new GL1_16_5());
    }
    
    @SuppressWarnings("deprecation")
    @Override public void alphaFuncEqual(float alpha) {
        RenderSystem.alphaFunc(GL_EQUAL,alpha);
    }
    
    @SuppressWarnings("deprecation")
    @Override public void alphaFuncGreater(float alpha) {
        RenderSystem.alphaFunc(GL_GREATER,alpha);
    }
    
    @SuppressWarnings("deprecation")
    @Override public void alphaFuncLesser(float alpha) {
        RenderSystem.alphaFunc(GL_LESS,alpha);
    }
    
    @Override public Object beginBuffer(Object buffer, int mode, Object vertexFormat) {
        ((BufferBuilder)buffer).begin(mode,(VertexFormat)vertexFormat);
        return buffer;
    }
    
    @Override public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getInstance().getTextureManager().bind(location.unwrap());
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
    
    @SuppressWarnings("deprecation")
    @Override public void disableAlpha() {
        RenderSystem.disableAlphaTest();
    }

    @Override public void disableBlend() {
        RenderSystem.disableBlend();
    }

    @Override public void disableCull() {
        RenderSystem.disableCull();
    }
    
    @SuppressWarnings("deprecation")
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
        int iX = x.intValue();
        int iY = y.intValue();
        int iWidth = width.intValue();
        int iHeight = height.intValue();
        int iMaxWidth = maxWidth.intValue();
        Screen curScreen = Minecraft.getInstance().screen;
        if(Objects.nonNull(curScreen)) {
            List<ITextComponent> unwrapped = font.unwrapTooltipComponents(lines);
            curScreen.renderComponentTooltip(unwrapMatrix(),unwrapped,iX,iY);
        }
        else font.renderToolTip(this,lines,iX,iY,iWidth,iHeight,iMaxWidth);
    }
    
    @SuppressWarnings("deprecation")
    @Override public void enableAlpha() {
        RenderSystem.enableAlphaTest();
    }

    @Override public void enableBlend() {
        RenderSystem.enableBlend();
    }

    @Override public void enableCull() {
        RenderSystem.enableCull();
    }
    
    @SuppressWarnings("deprecation")
    @Override public void enableLighting() {
        RenderSystem.enableLighting();
    }

    @Override public void enableTexture() {
        RenderSystem.enableTexture();
    }
    
    @Override public void endBuffer() {
        Tessellator.getInstance().end();
    }
    
    @Override public void endVertex(Object buffer) {
        ((BufferBuilder)buffer).endVertex();
    }
    
    @SuppressWarnings("unchecked")
    @Override public <B> B getBufferBuilder() {
        return (B)Tessellator.getInstance().getBuilder();
    }
    
    @Override public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        return new VertexWrapper1_16_5(mode,POSITION_COLOR,vertices,3,4);
    }
    
    @SuppressWarnings("deprecation")
    @Override public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapper1_16_5(mode,DefaultVertexFormats.POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    @Override public double getDirectMouseX() {
        return Minecraft.getInstance().mouseHandler.xpos();
    }
    
    @Override public double getDirectMouseY() {
        return Minecraft.getInstance().mouseHandler.ypos();
    }
    
    @Override public FontRenderer getFont() {
        return unwrapFont();
    }

    @Override public GLAPI getGLAPI() {
        return this.gl;
    }
    
    @Override public MatrixStack getMatrix() {
        return unwrapMatrix();
    }

    @Override public RenderAPI init(Object context) {
        setMatrix(context);
        return this;
    }
    
    @Override public void modelView() {} //I don't think this is applicable in 1.16.5?
    
    @Override public void popMatrix() {
        getMatrix().popPose();
    }
    
    @Override public void pushMatrix() {
        getMatrix().pushPose();
    }
    
    @Override public Object renderSourceImmediate() {
        return renderTypeImmediate(getBufferBuilder());
    }
    
    @Override public void resetTextureMatrix() {}
    
    @SuppressWarnings("deprecation")
    @Override public void rotate(float angle, float x, float y, float z) {
        RenderSystem.rotatef(angle,x,y,z);
    }
    
    @Override public void scale(float x, float y, float z) {
        if(Objects.nonNull(this.matrix)) getMatrix().scale(x, y, z);
    }
    
    @SuppressWarnings("deprecation")
    @Override public void setColor(float r, float g, float b, float a) {
        RenderSystem.color4f(r,g,b,a);
    }

    @Override public void setPosColorShader() {}
    
    @SuppressWarnings("deprecation")
    @Override public void translate(double x, double y, double z) {
        RenderSystem.translated(x,y,z);
    }
    
    @SuppressWarnings("deprecation")
    @Override public void translate(float x, float y, float z) {
        RenderSystem.translatef(x,y,z);
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