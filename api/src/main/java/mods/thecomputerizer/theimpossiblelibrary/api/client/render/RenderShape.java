package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Square;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;
import org.joml.Vector3d;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings("unused") @Getter @Setter
public class RenderShape extends AbstractWrapped<Shape> {
    
    public static RenderShape circle(RenderContext ctx) {
        return circle(ctx,1d,WHITE);
    }
    
    public static RenderShape circle(RenderContext ctx, double max) {
        return circle(ctx,max,WHITE);
    }
    
    public static RenderShape circle(RenderContext ctx, ColorCache color) {
        return circle(ctx,1d,color);
    }
    
    public static RenderShape circle(RenderContext ctx, double max, ColorCache color) {
        return new RenderShape(new Circle(Y.getDirection(),max,ctx.getHeightRatio()),color);
    }
    
    public static RenderShape square(RenderContext ctx) {
        return square(ctx,1d,WHITE);
    }
    
    public static RenderShape square(RenderContext ctx, double max) {
        return square(ctx,max,WHITE);
    }
    
    public static RenderShape square(RenderContext ctx, ColorCache color) {
        return square(ctx,1d,color);
    }
    
    public static RenderShape square(RenderContext ctx, double max, ColorCache color) {
        return new RenderShape(new Square(Y.getDirection(),max*2d,ctx.getHeightRatio()),color);
    }
    
    public static RenderShape square(RenderContext ctx, TextureWrapper texture) {
        return square(ctx,1d,texture);
    }
    
    public static RenderShape square(RenderContext ctx, double max, TextureWrapper texture) {
        RenderShape shape = square(ctx,max,WHITE);
        shape.texture = texture;
        return shape;
    }
    
    protected ColorCache color;
    protected TextureWrapper texture;
    
    public RenderShape(Shape wrapped) {
        this(wrapped,WHITE);
    }
    
    public RenderShape(Shape wrapped, ColorCache color) {
        super(wrapped);
        this.color = color;
    }
    
    public RenderShape copy() {
        RenderShape copy = new RenderShape(this.wrapped.copy(),this.color);
        copy.texture = this.texture;
        return copy;
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        if(this.wrapped instanceof Plane) {
            Plane plane = (Plane)this.wrapped;
            if(Objects.nonNull(this.texture)) ctx.drawTexturedPlane(center,plane,this.texture);
            else ctx.drawColoredPlane(center,plane,this.color);
        } else if(this.wrapped instanceof Circle) ctx.drawColoredCircle(center,(Circle)this.wrapped,100,this.color);
    }
    
    public double getDepth() {
        return this.wrapped.getDepth();
    }
    
    public double getHeight() {
        return this.wrapped.getHeight();
    }
    
    public double getWidth() {
        return this.wrapped.getWidth();
    }
    
    public void onResolutionUpdate(MinecraftWindow window) {
        if(this.wrapped instanceof Square) {
            Square square = (Square)this.wrapped;
            square.setSideLength(square.getSideLength(),window.getHeightScale());
        } else if(this.wrapped instanceof Circle) {
            Circle circle = (Circle)this.wrapped;
            circle.setHeightRatio(window.getHeightScale());
        }
    }
}
