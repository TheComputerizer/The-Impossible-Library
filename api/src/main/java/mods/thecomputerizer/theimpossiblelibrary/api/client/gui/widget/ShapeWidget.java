package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderFuzz;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShape;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShapeOutline;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;

import java.util.function.Supplier;

@SuppressWarnings("unused") @Setter
public class ShapeWidget extends Widget implements Wrapped<RenderShape> {
    
    public static ShapeWidget from(Shape shape) {
        return new ShapeWidget(RenderShape.from(shape),0d,0d);
    }
    
    public static ShapeWidget from(Shape shape, double x, double y) {
        return new ShapeWidget(RenderShape.from(shape),x,y);
    }
    
    public static ShapeWidget from(Shape shape, ColorCache color) {
        return new ShapeWidget(RenderShape.from(shape,color),0d,0d);
    }
    
    public static ShapeWidget from(Shape shape, ColorCache color, double x, double y) {
        return new ShapeWidget(RenderShape.from(shape,color),x,y);
    }
    
    public static ShapeWidget from(Shape shape, ResourceLocationAPI<?> texture) {
        return new ShapeWidget(RenderShape.from(shape,texture),0d,0d);
    }
    
    public static ShapeWidget from(Shape shape, ResourceLocationAPI<?> texture, double x, double y) {
        return new ShapeWidget(RenderShape.from(shape,texture),x,y);
    }
    
    public static ShapeWidget from(Shape shape, ResourceLocationAPI<?> texture, float alpha) {
        return new ShapeWidget(RenderShape.from(shape,texture,alpha),0d,0d);
    }
    
    public static ShapeWidget from(Shape shape, ResourceLocationAPI<?> texture, float alpha, double x, double y) {
        return new ShapeWidget(RenderShape.from(shape,texture,alpha),x,y);
    }
    
    public static ShapeWidget from(Shape shape, TextureWrapper texture) {
        return new ShapeWidget(RenderShape.from(shape,texture),0d,0d);
    }
    
    public static ShapeWidget from(Shape shape, TextureWrapper texture, double x, double y) {
        return new ShapeWidget(RenderShape.from(shape,texture),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape) {
        return of(RenderFuzz.from(shape));
    }
    
    public static ShapeWidget fuzz(Shape shape, double x, double y) {
        return of(RenderFuzz.from(shape),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, int max) {
        return of(RenderFuzz.from(shape,max));
    }
    
    public static ShapeWidget fuzz(Shape shape, int max, double x, double y) {
        return of(RenderFuzz.from(shape,max),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, Vector2i counts) {
        return of(RenderFuzz.from(shape,counts));
    }
    
    public static ShapeWidget fuzz(Shape shape, Vector2i counts, double x, double y) {
        return of(RenderFuzz.from(shape,counts),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, int minCount, int maxCount) {
        return of(RenderFuzz.from(shape,minCount,maxCount));
    }
    
    public static ShapeWidget fuzz(Shape shape, int minCount, int maxCount, double x, double y) {
        return of(RenderFuzz.from(shape,minCount,maxCount),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, Vector2i counts, Vector2f widths) {
        return of(RenderFuzz.from(shape,counts,widths));
    }
    
    public static ShapeWidget fuzz(Shape shape, Vector2i counts, Vector2f widths, double x, double y) {
        return of(RenderFuzz.from(shape,counts,widths),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth) {
        return of(RenderFuzz.from(shape,minCount,maxCount,minWidth,maxWidth));
    }
    
    public static ShapeWidget fuzz(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth, double x,
            double y) {
        return of(RenderFuzz.from(shape,minCount,maxCount,minWidth,maxWidth),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, Vector2i counts, Vector2f widths, Supplier<ColorCache> color) {
        return of(RenderFuzz.from(shape,counts,widths,color));
    }
    
    public static ShapeWidget fuzz(Shape shape, Vector2i counts, Vector2f widths, Supplier<ColorCache> color, double x,
            double y) {
        return of(RenderFuzz.from(shape,counts,widths,color),x,y);
    }
    
    public static ShapeWidget fuzz(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth,
            Supplier<ColorCache> color) {
        return of(RenderFuzz.from(shape,minCount,maxCount,minWidth,maxWidth,color));
    }
    
    public static ShapeWidget fuzz(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth,
            Supplier<ColorCache> color, double x, double y) {
        return of(RenderFuzz.from(shape,minCount,maxCount,minWidth,maxWidth,color),x,y);
    }
    
    public static ShapeWidget of(RenderShape shape) {
        return new ShapeWidget(shape,0d,0d);
    }
    
    public static ShapeWidget of(RenderShape shape, double x, double y) {
        return new ShapeWidget(shape,x,y);
    }
    
    public static ShapeWidget outlineFrom(Shape shape) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape)),0d,0d);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, double x, double y) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape)),x,y);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, float width) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape),width),0d,0d);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, float width, double x, double y) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape),width),x,y);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, ColorCache color) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape,color)),0d,0d);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, ColorCache color, double x, double y) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape,color)),x,y);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, ColorCache color, float width) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape,color),width),0d,0d);
    }
    
    public static ShapeWidget outlineFrom(Shape shape, ColorCache color, float width, double x, double y) {
        return new ShapeWidget(RenderShapeOutline.of(RenderShape.from(shape,color),width),x,y);
    }
    
    public static ShapeWidget outlineOf(RenderShape shape) {
        return new ShapeWidget(RenderShapeOutline.of(shape),0d,0d);
    }
    
    public static ShapeWidget outlineOf(RenderShape shape, double x, double y) {
        return new ShapeWidget(RenderShapeOutline.of(shape),x,y);
    }
    
    public static ShapeWidget outlineOf(RenderShape shape, float width) {
        return new ShapeWidget(RenderShapeOutline.of(shape,width),0d,0d);
    }
    
    public static ShapeWidget outlineOf(RenderShape shape, float width, double x, double y) {
        return new ShapeWidget(RenderShapeOutline.of(shape,width),x,y);
    }
    
    protected RenderShape shape;
    
    public ShapeWidget(RenderShape shape, double x, double y) {
        this.shape = shape;
        setX(x);
        setY(y);
    }
    
    @Override public ShapeWidget copy() {
        ShapeWidget copy = new ShapeWidget(this.shape,this.x,this.y);
        copy.height = this.height;
        copy.width = this.width;
        return copy;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        this.shape.draw(ctx,VectorHelper.copy3D(center).add(getX(),getY(),0d));
    }
    
    public Vector3d getCenterForGroup(Vector3d center) {
        return this.shape.getCenterForGroup(center);
    }
    
    @Override public double getHeight() {
        return this.shape.getHeight();
    }
    
    @Override public double getWidth() {
        return this.shape.getWidth();
    }
    
    @Override public void onResolutionUpdated(MinecraftWindow window) {
        this.shape.onResolutionUpdate(window);
    }
    
    @Override public RenderShape getWrapped() {
        return this.shape;
    }
    
    public boolean isInside(double x, double y, double z) {
        return this.shape.getWrapped().isInside(new Vector3d(x-getX(),y-getY(),z));
    }
    
    public void setColor(ColorCache color) {
        this.shape.setColor(color);
    }
    
    @Override public void setHeight(double height) {
        this.height = height;
        this.shape.setHeight(height);
    }
    
    public void setTexture(TextureWrapper texture) {
        this.shape.setTexture(texture);
    }
}