package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@Getter @Setter
public class RenderFuzz extends RenderShape {
    
    public static RenderFuzz from(Shape shape) {
        return from(shape,2,10,1f,1f,null);
    }
    
    public static RenderFuzz from(Shape shape, int max) {
        return from(shape,2,max,1f,1f,null);
    }
    
    public static RenderFuzz from(Shape shape, Vector2i counts) {
        return from(shape,counts.x,counts.y,1f,1f,null);
    }
    
    public static RenderFuzz from(Shape shape, int minCount, int maxCount) {
        return from(shape,minCount,maxCount,1f,1f,null);
    }
    
    public static RenderFuzz from(Shape shape, Vector2i counts, Vector2f widths) {
        //noinspection SuspiciousNameCombination
        return from(shape,counts.x,counts.y,widths.x,widths.y,null);
    }
    
    public static RenderFuzz from(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth) {
        return from(shape,minCount,maxCount,minWidth,maxWidth,null);
    }
    
    public static RenderFuzz from(Shape shape, Vector2i counts, Vector2f widths, Supplier<ColorCache> color) {
        //noinspection SuspiciousNameCombination
        return from(shape,counts.x,counts.y,widths.x,widths.y,color);
    }
    
    public static RenderFuzz from(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth,
            Supplier<ColorCache> color) {
        maxCount = Math.max(3,maxCount);
        minCount = Math.max(2,minCount);
        if(minCount>maxCount) minCount = maxCount;
        minWidth = Math.max(0.1f,minWidth);
        maxWidth = Math.min(10f,maxWidth);
        if(minWidth>maxWidth) minWidth = maxWidth;
        return new RenderFuzz(shape,minCount,maxCount,minWidth,maxWidth,color);
    }
    
    protected FuzzBall fuzz;
    
    public RenderFuzz(Shape shape, int minCount, int maxCount, float minWidth, float maxWidth,
            Supplier<ColorCache> color) {
        super(shape,WHITE);
        this.fuzz = shape.makeFuzzBall(minCount,maxCount,minWidth,maxWidth,color);
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        if(Objects.nonNull(this.fuzz)) this.fuzz.draw(ctx,center);
    }
}