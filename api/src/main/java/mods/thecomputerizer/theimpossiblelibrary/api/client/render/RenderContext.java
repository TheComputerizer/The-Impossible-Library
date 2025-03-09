package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape3D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector4;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@Getter
public final class RenderContext {
    
    public static RenderContext get(MinecraftAPI<?> mc) {
        return new RenderContext(mc);
    }
    
    private final MinecraftAPI<?> mc;
    private final FontAPI<?> font;
    private final RenderAPI renderer;
    private final RenderScale scale;
    @Setter private float partialTicks;
    
    RenderContext(MinecraftAPI<?> mc) {
        this.mc = mc;
        this.font = mc.getFont();
        this.renderer = mc.getRenderer();
        this.scale = new RenderScale(mc.getWindow());
    }
    
    public void drawArrow2D(Vector3 center, Plane bounds, Facing facing, float lineWidth, ColorCache color,
            boolean withTail) {
        double width = bounds.getWidth()/2d;
        double height = bounds.getHeight()/2d;
        prepareGradient(color);
        switch(facing) {
            case DOWN:
            case SOUTH: {
                drawArrow2D(center,0d,-height,-width,0d,lineWidth,withTail);
                break;
            }
            case EAST: {
                drawArrow2D(center,width,0d,0d,-height,lineWidth,withTail);
                break;
            }
            case UP:
            case NORTH:
            case WEST: {
                drawArrow2D(center,-width,0d,0d,height,lineWidth,withTail);
                break;
            }
            default: {
                drawArrow2D(center,0d,height,width,0d,lineWidth,withTail);
                break;
            }
        }
    }
    
    public void drawArrow2D(Vector3 center, double tipX, double tipY, double prongX, double prongY, float width,
            boolean withTail) {
        Vector3 tip = center.add(tipX,tipY,0d,new Vector3());
        drawLine(tip,center.dX()+prongX,center.dY()+prongY,center.dZ(),width);
        drawLine(tip,center.dX()-prongX,center.dY()-prongY,center.dZ(),width);
        if(withTail) drawLine(tip,center.dX()-tipX,center.dY()-tipY,center.dZ(),width);
    }
    
    public void drawColoredBox(Box box, ColorCache color) {
        for(Shape2D shape : box.getAs2DArray()) drawColoredPlane(box.center,(Plane)shape,color);
    }
    
    public void drawColoredCircle(Vector3 center, Circle circle, ColorCache color) {
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
    
    public void drawColoredPlane(Vector3 center, Plane plane, ColorCache color) {
        if(!plane.checkToleranceBounds(center,this.scale.getRenderBounds())) return;
        Vector2 min = plane.getRelativeMin();
        Vector2 max = plane.getRelativeMax();
        prepareGradient(color);
        VertexWrapper buffer = initQuads(false);
        withScaledPos(buffer,center,min).color(color).endVertex();
        withScaledPos(buffer,center,max.dX(),min.dY()).color(color).endVertex();
        withScaledPos(buffer,center,max).color(color).endVertex();
        withScaledPos(buffer,center,min.dX(),max.dY()).color(color).endVertex();
        finishGradient(buffer);
    }
    
    public void drawLine(Vector3 start, Vector3 end, float width) {
        drawLine(start.dX(),start.dY(),start.dZ(),end.dX(),end.dY(),end.dZ(),width);
    }
    
    public void drawLine(Vector3 start, double endX, double endY, double endZ, float width) {
        drawLine(start.dX(),start.dY(),start.dZ(),endX,endY,endZ,width);
    }
    
    public void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ,
            float width) {
        GLAPI gl = prepareLine(GLAPI::lines,width);
        double dStartX = withDisplayScaledX(startX);
        double dStartY = withDisplayScaledY(startY);
        double dStartZ = withScaledZ(startZ);
        double dEndX = withDisplayScaledX(endX);
        double dEndY = withDisplayScaledY(endY);
        double dEndZ = withScaledZ(endZ);
        gl.normalizedVertex(dStartX,dStartY,dStartZ,dEndX,dEndY,dEndZ);
        gl.normalizedVertex(dEndX,dEndY,dEndZ,dStartX,dStartY,dStartZ);
        finishLine(gl);
    }
    
    public void drawOutline(Vector3 center, Shape2D shape, float width, ColorCache color) {
        drawOutline(center,shape.getOutlineSupplier(this.scale.getRenderBounds()),width,color);
    }
    
    public void drawOutline(Vector3 center, VectorSupplier2D vectors, float width, ColorCache color) {
        GLAPI gl = prepareLine(GLAPI::lineStrip,width,color);
        Vector2 first = null;
        Vector2 last = null;
        Vector2 vec = null;
        while(vectors.hasNext()) {
            Vector2 next = vectors.getNext();
            double nextX = this.scale.applyXForScreen(center.dX(),next.dX());
            double nextY = this.scale.applyYForScreen(center.dY(),next.dY());
            if(Objects.isNull(vec)) {
                first = new Vector2(nextX,nextY);
                vec = next;
                continue;
            }
            last = new Vector2(nextX,nextY);
            double x = this.scale.applyXForScreen(center.dX(),vec.dX());
            double y = this.scale.applyYForScreen(center.dY(),vec.dY());
            gl.normalizedVertex2D(x,y,color,nextX,nextY);
            vec = next;
        }
        if(Objects.nonNull(first) && Objects.nonNull(last)) gl.normalizedVertex2D(last,color,first);
        finishLine(gl);
    }
    
    public void drawOutline(Vector3 center, Shape3D shape, float width, ColorCache color) {
        drawOutline(center,shape.getOutlineSupplier(this.scale.getRenderBounds()),width,color);
    }
    
    public void drawOutline(Vector3 center, VectorSupplier3D vectors, float width, ColorCache color) {
        GLAPI gl = prepareLine(GLAPI::lineStrip,width,color);
        Vector3 first = null;
        Vector3 last = null;
        Vector3 vec = null;
        while(vectors.hasNext()) {
            Vector3 next = vectors.getNext();
            double nextX = this.scale.applyXForScreen(center.dX(),next.dX());
            double nextY = this.scale.applyYForScreen(center.dY(),next.dY());
            double nextZ = this.scale.applyZForScreen(center.dZ(),next.dZ());
            if(Objects.isNull(vec)) {
                vec = next;
                first = new Vector3(nextX,nextY,nextZ);
                continue;
            }
            last = new Vector3(nextX,nextY,nextZ);
            double x = this.scale.applyXForScreen(center.dX(),vec.dX());
            double y = this.scale.applyYForScreen(center.dY(),vec.dY());
            double z = this.scale.applyZForScreen(center.dZ(),vec.dZ());
            gl.normalizedVertex(x,y,z,color,nextX,nextY,nextZ);
            vec = next;
        }
        if(Objects.nonNull(first) && Objects.nonNull(last)) gl.normalizedVertex(last,color,first);
        finishLine(gl);
    }
    
    public void drawTexturedPlane(Vector3 center, Plane plane, TextureWrapper texture) {
        drawTexturedPlane(center,plane,texture.getTexture(),texture.getVectorUV(),texture.getColorMask(true));
    }
    
    public void drawTexturedPlane(Vector3 center, Plane plane, ResourceLocationAPI<?> texture, Vector4 uv,
            ColorCache mask) {
        if(Objects.isNull(texture) || isNotBounded(center)) return;
        Vector2 min = plane.getRelativeMin();
        Vector2 max = plane.getRelativeMax();
        this.renderer.bindTexture(texture);
        prepareTexture(mask);
        VertexWrapper buffer = initQuads(true);
        withScaledPos(buffer,center,min).tex(uv.dX(),uv.dW()).color(mask).endVertex();
        withScaledPos(buffer,center,max.dX(),min.dY()).tex(uv.dZ(),uv.dW()).color(mask).endVertex();
        withScaledPos(buffer,center,max).tex(uv.dZ(),uv.dY()).color(mask).endVertex();
        withScaledPos(buffer,center,min.dX(),max.dY()).tex(uv.dX(),uv.dY()).color(mask).endVertex();
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
    
    public void finishLine(GLAPI gl) {
        gl.directEnd();
        gl.setLineWidth(1f);
        this.renderer.setColor(1f,1f,1f,1f);
        this.renderer.enableTexture();
        this.renderer.disableBlend();
        this.renderer.defaultBlendFunc();
        //this.renderer.modelView();
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
    
    public boolean isNotBounded(Vector3 pos) {
        return !this.scale.canDraw(pos);
    }
    
    public boolean isWide() {
        return this.scale.getScreenRatio()<1d;
    }
    
    public void prepareGradient(ColorCache bgColor) {
        this.renderer.enableBlend();
        this.renderer.defaultBlendFunc();
        this.renderer.setColor(bgColor);
        this.renderer.disableTexture();
    }
    
    public GLAPI prepareLine(Function<GLAPI,Integer> mode, float width) {
        return prepareLine(mode,width,WHITE);
    }
    
    public GLAPI prepareLine(Function<GLAPI,Integer> mode, float width, ColorCache color) {
        GLAPI gl = this.renderer.getGLAPI();
        //this.renderer.modelView();
        this.renderer.enableBlend();
        this.renderer.blendTranslucent();
        this.renderer.setColor(color);
        this.renderer.disableTexture();
        gl.setWorkingMatrix(this.renderer.matrix);
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
    
    public void scissorScaled(double left, double bottom, double width, double height) {
        int iLeft = (int)(withDisplayScaledX(left));
        int iBottom = (int)((withDisplayScaledY(bottom)));
        int iWidth = Math.abs((int)(withDisplayScaledX(width)));
        int iHeight = Math.abs((int)((withDisplayScaledY(height))));
        this.renderer.getGLAPI().scissor(iLeft,iBottom,iWidth,iHeight);
    }
    
    public String trimStringScaled(String text, double width) {
        return this.font.trimStringTo(text,withScreenScaledX(-1d+width));
    }
    
    public List<String> splitLines(String text, double width) {
        return FontHelper.splitLines(this.font,text,withScreenScaledX(-1d+width));
    }
    
    public void updateResolution(MinecraftWindow window) {
        updateResolution(window.getWidth(),window.getHeight(),window.getDisplayWidth(),window.getDisplayHeight());
    }
    
    public void updateResolution(double screenWidth, double screenHeight, double displayWidth, double displayHeight) {
        this.scale.updateResolution(screenWidth,screenHeight,displayWidth,displayHeight);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3 center, Vector2 pos) {
        return withScaledPos(buffer,center,pos.dX(),pos.dY(),0d);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3 center, Vector3 pos) {
        return withScaledPos(buffer,center,pos.dX(),pos.dY(),pos.dZ());
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3 center, double x, double y) {
        return withScaledPos(buffer,center,x,y,0d);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3 center, double x, double y, double z) {
        return this.scale.applyForScreen(buffer,center,x,y,z);
    }
    
    public double withDisplayScaledX(double x) {
        return ((x*this.scale.getModScaleX())+this.scale.getTransformX()+1d)/this.scale.getDisplayScaleX();
    }
    
    public double withDisplayScaledY(double y) {
        return this.scale.getDisplayHeight()-(((y*this.scale.getModScaleY())+this.scale.getTransformY()+1d)/this.scale.getDisplayScaleY());
    }
    
    public double withScaledZ(double z) {
        return (z*this.scale.getModScaleZ())+this.scale.getTransformZ();
    }
    
    public double withScreenScaledX(double x) {
        return ((x*this.scale.getModScaleX())+this.scale.getTransformX()+1d)/this.scale.getScreenScaleX();
    }
    
    public double withScreenScaledY(double y) {
        return this.scale.getScreenHeight()-(((y*this.scale.getModScaleY())+this.scale.getTransformY()+1d)/this.scale.getScreenScaleY());
    }
}