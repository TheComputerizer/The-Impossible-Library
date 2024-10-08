package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector4f;

import java.util.Collection;

@Getter
public abstract class RenderAPI {
    
    protected final GLAPI gl;
    @Setter protected int mouseX;
    @Setter protected int mouseY;
    @Setter protected Object matrix;
    @Setter protected Object font;
    
    protected RenderAPI(GLAPI gl) {
        this.gl = gl;
    }

    @IndirectCallers public abstract void alphaFuncEqual(float alpha);
    public abstract void alphaFuncGreater(float alpha);
    @IndirectCallers public abstract void alphaFuncLesser(float alpha);
    public abstract void bindTexture(ResourceLocationAPI<?> location);
    public abstract void defaultBlendFunc();
    public abstract void depthMask(boolean mask);
    @IndirectCallers public abstract void disableAlpha();
    public abstract void disableBlend();
    public abstract void disableCull();
    public abstract void disableLighting();
    public abstract void disableTexture();
    
    @IndirectCallers
    public void drawCenteredString(FontAPI<?> font, TextBuffer text, Number x, Number y) {
        drawCenteredString(font,text.getText().getApplied(),x,y,text.getColor().getColorI());
    }
    
    @IndirectCallers
    public void drawCenteredString(FontAPI<?> font, TextAPI<?> text, Number x, Number y, ColorCache color) {
        drawCenteredString(font,text.getApplied(),x,y,color.getColorI());
    }
    
    public abstract void drawCenteredString(FontAPI<?> font, String str, Number x, Number y, int color);
    
    @IndirectCallers
    public void drawString(FontAPI<?> font, TextBuffer text, Number left, Number top) {
        drawString(font,text.getText().getApplied(),left,top,text.getColor().getColorI());
    }
    
    @IndirectCallers
    public void drawString(FontAPI<?> font, TextAPI<?> text, Number left, Number top, ColorCache color) {
        drawString(font,text.getApplied(),left,top,color.getColorI());
    }
    
    public abstract void drawString(FontAPI<?> font, String str, Number left, Number top, int color);
    public abstract void drawTooltip(FontAPI<?> font, Collection<TextAPI<?>> lines, Number x, Number y, Number width,
            Number height, Number maxWidth);
    public abstract void enableAlpha();
    public abstract void enableBlend();
    public abstract void enableCull();
    public abstract void enableLighting();
    public abstract void enableTexture();
    /**
     * POSITION_COLOR
     */
    public abstract VertexWrapper getBufferBuilderPC(int mode, int vertices);
    /**
     * POSITION_TEX_COLOR
     */
    public abstract VertexWrapper getBufferBuilderPTC(int mode, int vertices);
    public abstract double getDirectMouseX();
    public abstract double getDirectMouseY();
    public abstract GLAPI getGLAPI();
    public abstract RenderAPI init(Object context);
    public abstract void popMatrix();
    public abstract void pushMatrix();
    @IndirectCallers public abstract void resetTextureMatrix();
    public abstract void rotate(float angle, float x, float y, float z);
    public abstract void scale(float x, float y, float z);
    
    public void setColor(ColorCache color) {
        Vector4f colorVec = color.getColorVF();
        setColor(colorVec.x,colorVec.y,colorVec.z,colorVec.w);
    }
    
    public abstract void setColor(float r, float g, float b, float a);
    
    @IndirectCallers public abstract void setPosColorShader();
    public abstract void translate(double x, double y, double z);
    public abstract void translate(float x, float y, float z);
    
    @IndirectCallers
    @SuppressWarnings("unchecked")
    public <F> F unwrapFont() {
        return (F)this.font;
    }
    
    @IndirectCallers
    @SuppressWarnings("unchecked")
    public <M> M unwrapMatrix() {
        return (M)this.matrix;
    }
}