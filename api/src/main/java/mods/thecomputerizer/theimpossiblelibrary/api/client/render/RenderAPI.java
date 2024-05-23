package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector4f;

import java.util.Collection;

@SuppressWarnings("unused")
public interface RenderAPI {

    void alphaFuncEqual(float alpha);
    void alphaFuncGreater(float alpha);
    void alphaFuncLesser(float alpha);
    void bindTexture(ResourceLocationAPI<?> location);
    void defaultBlendFunc();
    void depthMask(boolean mask);
    void disableAlpha();
    void disableBlend();
    void disableCull();
    void disableLighting();
    void disableTexture();
    
    default void drawCenteredString(FontAPI font, TextBuffer text, Number x, Number y) {
        drawCenteredString(font,text.getText().getApplied(),x,y,text.getColor().getColorI());
    }
    
    default void drawCenteredString(FontAPI font, TextAPI<?> text, Number x, Number y, ColorCache color) {
        drawCenteredString(font,text.getApplied(),x,y,color.getColorI());
    }
    
    void drawCenteredString(FontAPI font, String str, Number x, Number y, int color);
    
    default void drawString(FontAPI font, TextBuffer text, Number left, Number top) {
        drawString(font,text.getText().getApplied(),left,top,text.getColor().getColorI());
    }
    
    default void drawString(FontAPI font, TextAPI<?> text, Number left, Number top, ColorCache color) {
        drawString(font,text.getApplied(),left,top,color.getColorI());
    }
    
    void drawString(FontAPI font, String str, Number left, Number top, int color);
    void drawTooltip(FontAPI font, Collection<TextAPI<?>> lines, Number x, Number y, Number width, Number height, Number maxWidth);
    void enableAlpha();
    void enableBlend();
    void enableCull();
    void enableLighting();
    void enableTexture();
    /**
     * POSITION_COLOR
     */
    VertexWrapper getBufferBuilderPC(int mode, int vertices);
    /**
     * POSITION_TEX_COLOR
     */
    VertexWrapper getBufferBuilderPTC(int mode, int vertices);
    GLAPI getGLAPI();
    RenderAPI init(Object context);
    void popMatrix();
    void pushMatrix();
    void resetTextureMatrix();
    void rotate(float angle, float x, float y, float z);
    void scale(float x, float y, float z);
    
    default void setColor(ColorCache color) {
        Vector4f colorVec = color.getColorVF();
        setColor(colorVec.x,colorVec.y,colorVec.z,colorVec.w);
    }
    
    void setColor(float r, float g, float b, float a);
    /**
     * Unused in 1.12.2
     */
    void setMatrix(Object matrix);
    void setPosColorShader();
    void translate(double x, double y, double z);
    void translate(float x, float y, float z);
}
