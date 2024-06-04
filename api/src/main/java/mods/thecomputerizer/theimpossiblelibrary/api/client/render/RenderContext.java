package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape3D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused") @Getter
public final class RenderContext {
    
    public static RenderContext get(MinecraftAPI mc) {
        return new RenderContext(mc);
    }
    
    private final MinecraftAPI mc;
    private final FontAPI font;
    private final RenderAPI renderer;
    private final RenderScale scale;
    @Setter private float partialTicks;
    
    RenderContext(MinecraftAPI mc) {
        this.mc = mc;
        this.font = mc.getFont();
        this.renderer = mc.getRenderer();
        this.scale = new RenderScale(mc.getWindow());
    }
    
    public boolean isNotBounded(Vector3d pos) {
        return !this.scale.canDraw(pos);
    }
    
    public void drawColoredBox(Box box, ColorCache color) {
        for(Shape2D shape : box.getAs2DArray()) drawColoredPlane(box.center,(Plane)shape,color);
    }
    
    public void drawColoredCircle(Vector3d center, Circle circle, ColorCache color) {
        if(!circle.checkToleranceBounds(center,this.scale.getRenderBounds())) return;
        VectorSupplier2D vectors = circle.getVectorSupplier(this.scale.getRenderBounds());
        while(vectors.hasNext()) {
            prepareGradient(color);
            VertexWrapper buffer = initQuads(false);
            withScaledPos(buffer,center,vectors.getNext()).color(color).endVertex();
            withScaledPos(buffer,center,vectors.getNext()).color(color).endVertex();
            withScaledPos(buffer,center,vectors.getNext()).color(color).endVertex();
            withScaledPos(buffer,center,vectors.getNext()).color(color).endVertex();
            finishGradient(buffer);
        }
    }
    
    public void drawColoredPlane(Vector3d center, Plane plane, ColorCache color) {
        if(!plane.checkToleranceBounds(center,this.scale.getRenderBounds())) return;
        Vector2d min = plane.getRelativeMin();
        Vector2d max = plane.getRelativeMax();
        prepareGradient(color);
        VertexWrapper buffer = initQuads(false);
        withScaledPos(buffer,center,min).color(color).endVertex();
        withScaledPos(buffer,center,max.x,min.y).color(color).endVertex();
        withScaledPos(buffer,center,max).color(color).endVertex();
        withScaledPos(buffer,center,min.x,max.y).color(color).endVertex();
        finishGradient(buffer);
    }
    
    public void drawLine(Vector3d start, Vector3d end, float width) {
        GLAPI gl = prepareLine(GLAPI::lines,width);
        gl.directVertexD(start.x,start.y,start.z);
        gl.directVertexD(end.x,end.y,end.z);
        gl.directEnd();
    }
    
    public void drawOutline(Vector3d center, Shape2D shape, float width, ColorCache color) {
        drawOutline(center,shape.getOutlineSupplier(this.scale.getRenderBounds()),width,color);
    }
    
    public void drawOutline(Vector3d center, VectorSupplier2D vectors, float width, ColorCache color) {
        prepareGradient(color);
        GLAPI gl = prepareLine(GLAPI::lineLoop,width);
        while(vectors.hasNext()) {
            Vector2d next = vectors.getNext();
            next = new Vector2d(this.scale.applyXForScreen(center.x,next.x),this.scale.applyYForScreen(center.y,next.y));
            gl.directVertexD(next.x,next.y,0d);
        }
        gl.directEnd();
        this.renderer.enableTexture();
        this.renderer.disableBlend();
    }
    
    public void drawOutline(Vector3d center, Shape3D shape, float width, ColorCache color) {
        drawOutline(center,shape.getOutlineSupplier(this.scale.getRenderBounds()),width,color);
    }
    
    public void drawOutline(Vector3d center, VectorSupplier3D vectors, float width, ColorCache color) {
        prepareGradient(color);
        GLAPI gl = prepareLine(GLAPI::lineLoop,width);
        while(vectors.hasNext()) {
            Vector3d next = vectors.getNext();
            next = new Vector3d(this.scale.applyXForScreen(center.x,next.x),this.scale.applyYForScreen(center.y,next.y),
                                this.scale.applyZForScreen(center.z,next.z));
            gl.directVertexD(next.x,next.y,next.z);
        }
        gl.directEnd();
        this.renderer.enableTexture();
        this.renderer.disableBlend();
    }
    
    public void drawTexturedPlane(Vector3d center, Plane plane, TextureWrapper texture) {
        drawTexturedPlane(center,plane,texture.getTexture(),texture.getVectorUV(),texture.getColorMask(true));
    }
    
    public void drawTexturedPlane(Vector3d center, Plane plane, ResourceLocationAPI<?> texture, Vector4d uv,
            ColorCache mask) {
        if(isNotBounded(center)) return;
        Vector2d min = plane.getRelativeMin();
        Vector2d max = plane.getRelativeMax();
        this.renderer.bindTexture(texture);
        prepareTexture(mask);
        VertexWrapper buffer = initQuads(true);
        withScaledPos(buffer,center,min).tex(uv.x,uv.w).color(mask).endVertex();
        withScaledPos(buffer,center,max.x,min.y).tex(uv.z,uv.w).color(mask).endVertex();
        withScaledPos(buffer,center,max).tex(uv.z,uv.y).color(mask).endVertex();
        withScaledPos(buffer,center,min.x,max.y).tex(uv.x,uv.y).color(mask).endVertex();
        finishTexture(buffer);
    }
    
    public void drawTooltip(Collection<TextAPI<?>> text, double x, double y) {
        drawTooltip(text,x,y,-1d);
    }
    
    public void drawTooltip(Collection<TextAPI<?>> text, double x, double y, double maxWidth) {
        this.renderer.drawTooltip(this.font,text,this.scale.applyXForScreen(0d,x),
                this.scale.applyYForScreen(0d,y), this.scale.getScreenWidth(),this.scale.getScreenHeight(),
                                  maxWidth);
    }
    
    public void finishGradient(VertexWrapper buffer) {
        buffer.finish();
        this.renderer.enableTexture();
        this.renderer.disableBlend();
    }
    
    public void finishTexture(VertexWrapper buffer) {
        buffer.finish();
        this.renderer.disableBlend();
    }
    
    public double getScaledMouseX() {
        return this.scale.normalizeDisplayX(this.renderer.getDirectMouseX());
    }
    
    public double getScaledMouseY() {
        return this.scale.normalizeDisplayY(this.renderer.getDirectMouseY());
    }
    
    public double getHeightRatio() {
        return this.scale.getScreenRatio();
    }
    
    public double getScaledFontHeight() {
        return ((double)this.font.getFontHeight())*this.scale.getScreenScaleY();
    }
    
    public double getScaledStringWidth(String str) {
        return ((double)this.font.getStringWidth(str))*this.scale.getScreenScaleX();
    }
    
    public VertexWrapper initQuads(boolean textured) {
        int quads = this.renderer.getGLAPI().quads();
        VertexWrapper buffer = textured ? this.renderer.getBufferBuilderPTC(quads,4) :
                this.renderer.getBufferBuilderPC(quads,4);
        buffer.start();
        return buffer;
    }
    
    public boolean isWide() {
        return this.scale.getScreenRatio()<1d;
    }
    
    public void prepareGradient(ColorCache bgColor) {
        this.renderer.enableBlend();
        this.renderer.disableTexture();
        this.renderer.defaultBlendFunc();
        this.renderer.setColor(bgColor);
    }
    
    public GLAPI prepareLine(Function<GLAPI,Integer> mode, float width) {
        GLAPI gl = this.renderer.getGLAPI();
        gl.setLineWidth(width);
        gl.directBegin(mode.apply(gl));
        return gl;
    }
    
    public void prepareTexture(ColorCache bgColor) {
        this.renderer.enableBlend();
        this.renderer.defaultBlendFunc();
        this.renderer.enableAlpha();
        this.renderer.setColor(bgColor);
    }
    
    public GLAPI prepareTriangleFan() {
        GLAPI gl = this.renderer.getGLAPI();
        gl.directBegin(gl.triangleFan());
        return gl;
    }
    
    public void scissorScaled(double left, double bottom, double width, double height) {
        int iLeft = (int)(withDisplayScaledX(left));
        int iBottom = (int)((withDisplayScaledY(bottom)));
        int iWidth = Math.abs((int)(withDisplayScaledX(width)));
        int iHeight = Math.abs((int)((withDisplayScaledY(height))));
        this.renderer.getGLAPI().scissor(iLeft,iBottom,iWidth,iHeight);
    }
    
    public String trimStringScaled(String text, double width) {
        return this.font.trimStringTo(text,withScreenScaledX(width));
    }
    
    public List<String> splitLines(String text, double width) {
        return FontHelper.splitLines(this.font,text,withScreenScaledX(width));
    }
    
    public void updateResolution(MinecraftWindow window) {
        updateResolution(window.getWidth(),window.getHeight(),window.getDisplayWidth(),window.getDisplayHeight());
    }
    
    public void updateResolution(double screenWidth, double screenHeight, double displayWidth, double displayHeight) {
        this.scale.updateResolution(screenWidth,screenHeight,displayWidth,displayHeight);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, Vector2d pos) {
        return withScaledPos(buffer,center,pos.x,pos.y,0d);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, Vector3d pos) {
        return withScaledPos(buffer,center,pos.x,pos.y,pos.z);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, double x, double y) {
        return withScaledPos(buffer,center,x,y,0d);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, double x, double y, double z) {
        return this.scale.applyForScreen(buffer, center, x, y, z);
    }
    
    public double withDisplayScaledX(double x) {
        return ((x*this.scale.getModScaleX())+this.scale.getTransformX()+1d)/this.scale.getDisplayScaleX();
    }
    
    public double withDisplayScaledY(double y) {
        return this.scale.getDisplayHeight()-(((y*this.scale.getModScaleY())+this.scale.getTransformY()+1d)/this.scale.getDisplayScaleY());
    }
    
    public double withScreenScaledX(double x) {
        return ((x*this.scale.getModScaleX())+this.scale.getTransformX()+1d)/this.scale.getScreenScaleX();
    }
    
    public double withScreenScaledY(double y) {
        return this.scale.getScreenHeight()-(((y*this.scale.getModScaleY())+this.scale.getTransformY()+1d)/this.scale.getScreenScaleY());
    }
}