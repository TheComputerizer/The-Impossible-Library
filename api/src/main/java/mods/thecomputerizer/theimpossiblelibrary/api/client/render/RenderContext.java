package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle.CircleSlice;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.util.Collection;
import java.util.function.Consumer;
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
    
    public VertexWrapper bufferColor(VertexWrapper buffer, ColorCache color) {
        return buffer.color(color.getColorVF());
    }
    
    public boolean isNotBounded(Vector3d pos) {
        return !this.scale.canDraw(pos);
    }
    
    public void drawColoredBox(Box box, ColorCache color) {
    
    }
    
    public void drawColoredPlane(Vector3d center, Plane plane, ColorCache color) {
        drawColoredPlane(center,plane,0d,0d,color);
    }
    
    public void drawColoredPlane(Vector3d center, Plane plane, double innerWidth, double innerHeight, ColorCache color) {
        drawColoredPlane(center,plane,new Vector2d(innerWidth,innerHeight),color);
    }
    
    public void drawColoredPlane(Vector3d center, Plane plane, Vector2d innerBounds, ColorCache color) {
        if(innerBounds.x>0d || innerBounds.y>0d) {
            for(Plane outline : Plane.getOutlinePlanes(plane,plane.getScaled(innerBounds)))
                drawColoredPlane(center,outline,color);
            return;
        }
        if(isNotBounded(center)) return;
        Vector2d min = plane.getRelativeMin();
        Vector2d max = plane.getRelativeMax();
        initSolidColor();
        Consumer<VertexWrapper> finisher = wrapper -> supplyVertex(wrapper,w -> bufferColor(w,color));
        VertexWrapper buffer = initQuads(false);
        finisher.accept(withScaledPos(buffer,center,min));
        finisher.accept(withScaledPos(buffer,center,min.x,max.y));
        finisher.accept(withScaledPos(buffer,center,max.x,min.y));
        finisher.accept(withScaledPos(buffer,center,max));
        buffer.finish();
        finishSolidColor();
    }
    
    public void drawColoredCircle(Vector3d center, Circle circle, int resolution, ColorCache color) {
        for(CircleSlice slice : circle.slice(resolution)) drawColoredCircle(center,circle,color);
    }
    
    public void drawColoredCircle(Vector3d center, Circle circle, ColorCache color) {
        if(isNotBounded(center)) return;
        double[] coords = circle.getQuadCoords();
        initSolidColor();
        Consumer<VertexWrapper> finisher = wrapper -> supplyVertex(wrapper,w -> bufferColor(w,color));
        VertexWrapper buffer = initQuads(false);
        finisher.accept(withScaledPos(buffer,center,circle.getWorldCoordinate(new Vector2d(coords[0],coords[1]))));
        finisher.accept(withScaledPos(buffer,center,circle.getWorldCoordinate(new Vector2d(coords[2],coords[3]))));
        finisher.accept(withScaledPos(buffer,center,circle.getWorldCoordinate(new Vector2d(coords[4],coords[5]))));
        finisher.accept(withScaledPos(buffer,center,circle.getWorldCoordinate(new Vector2d(coords[6],coords[7]))));
        buffer.finish();
        finishSolidColor();
    }
    
    public void drawLine(Vector3d start, Vector3d end, double thickness) {
        GLAPI gl = renderer.getGLAPI();
        gl.directBegin(gl.lines());
        gl.directVertex((float)start.x,(float)start.y,(float)start.z);
        gl.directVertex((float)end.x,(float)end.y,(float)end.z);
        gl.directEnd();
    }
    
    public void drawText(Vector3d center, TextBuffer text) {
        drawText(center,text,ColorHelper.WHITE);
    }
    
    public void drawText(Vector3d center, TextBuffer text, ColorCache color) {
        this.renderer.drawCenteredString(this.font,text.getText().getApplied(),(int)this.scale.applyX(0d,center.x),
                (int)scale.applyY(0d,center.y),color.getColorI());
    }
    
    public void drawTexturedPlane(Vector3d center, Plane plane, TextureWrapper texture) {
        drawTexturedPlane(center,plane,texture.getTexture(),new Vector4d(
                texture.getMinU(),texture.getMinV(),texture.getMaxU(),texture.getMaxV()),
                          texture.getColorMask().withAlpha(texture.getAlpha()));
    }
    
    public void drawTexturedPlane(Vector3d center, Plane plane, ResourceLocationAPI<?> texture, Vector4d uv,
            ColorCache mask) {
        if(isNotBounded(center)) return;
        Vector2d min = plane.getRelativeMin();
        Vector2d max = plane.getRelativeMax();
        this.renderer.bindTexture(texture);
        initSolidColor();
        Consumer<VertexWrapper> finisher = wrapper -> supplyVertex(wrapper, w -> bufferColor(w,mask));
        VertexWrapper buffer = initQuads(true);
        withScaledPos(buffer,center,min.x,min.y).tex(uv.x,uv.w).color(mask).endVertex();
        withScaledPos(buffer,center,max.x,min.y).tex(uv.z,uv.w).color(mask).endVertex();
        withScaledPos(buffer,center,max.x,max.y).tex(uv.z,uv.y).color(mask).endVertex();
        withScaledPos(buffer,center,min.x,max.y).tex(uv.x,uv.y).color(mask).endVertex();
        buffer.finish();
        finishSolidColor();
    }
    
    public void drawTooltip(Collection<TextAPI<?>> text, double x, double y) {
        drawTooltip(text,x,y,-1d);
    }
    
    public void drawTooltip(Collection<TextAPI<?>> text, double x, double y, double maxWidth) {
        this.renderer.drawTooltip(this.font,text,(int)this.scale.applyX(0d,x),(int)scale.applyY(0d,y),
                                  (int)this.scale.getWidth(),(int)this.scale.getHeight(),(int)maxWidth);
    }
    
    public void finishSolidColor() {
        this.renderer.resetTextureMatrix();
        this.renderer.disableBlend();
    }
    
    public double getHeightRatio() {
        return this.scale.getHeightRatio();
    }
    
    public void initSolidColor() {
        this.renderer.enableBlend();
        this.renderer.resetTextureMatrix();
        this.renderer.defaultBlendFunc();
        this.renderer.setPosColorShader();
    }
    
    public VertexWrapper initQuads(boolean textured) {
        int quads = this.renderer.getGLAPI().quads();
        VertexWrapper buffer = textured ? this.renderer.getBufferBuilderPTC(quads,4) :
                this.renderer.getBufferBuilderPC(quads,4);
        buffer.start();
        return buffer;
    }
    
    public boolean isWide() {
        return this.scale.getHeightRatio()<1d;
    }
    
    public void supplyVertex(VertexWrapper wrapper, Function<VertexWrapper,VertexWrapper> builder) {
        builder.apply(wrapper).endVertex();
    }
    
    public void updateResolution(MinecraftWindow window) {
        updateResolution(window.getWidth(),window.getHeight());
    }
    
    public void updateResolution(double width, double height) {
        this.scale.updateResolution(width,height);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, Vector2d pos) {
        return withScaledPos(buffer,center,pos.x,pos.y,1d);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, Vector3d pos) {
        return withScaledPos(buffer,center,pos.x,pos.y,pos.z);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, double x, double y) {
        return withScaledPos(buffer,center,x,y,1d);
    }
    
    private VertexWrapper withScaledPos(VertexWrapper buffer, Vector3d center, double x, double y, double z) {
        return this.scale.apply(buffer,center,x,y,z);
    }
}