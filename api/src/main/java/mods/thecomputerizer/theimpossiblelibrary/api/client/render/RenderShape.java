package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Square;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MutableWrapped;
import org.joml.Vector3d;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused") @Getter
public class RenderShape extends MutableWrapped<Shape> {
    
    public static RenderShape from(Shape shape) {
        return new RenderShape(shape,WHITE);
    }
    
    public static RenderShape from(Shape shape, ColorCache color) {
        return new RenderShape(shape,color);
    }
    
    public static RenderShape from(Shape shape, ResourceLocationAPI<?> texture) {
        return new RenderShape(shape,WHITE).setTexture(texture);
    }
    
    public static RenderShape from(Shape shape, ResourceLocationAPI<?> texture, float alpha) {
        return new RenderShape(shape,WHITE).setTexture(texture,alpha);
    }
    
    public static RenderShape from(Shape shape, TextureWrapper texture) {
        return new RenderShape(shape,WHITE).setTexture(texture);
    }
    
    protected ColorCache color;
    protected TextureWrapper texture;
    
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
        } else if(this.wrapped instanceof Circle) ctx.drawColoredCircle(center,(Circle)this.wrapped,this.color);
    }
    
    public Vector3d getCenterForGroup(Vector3d center) {
        return this.wrapped.getCenter(center);
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
    
    public RenderShape setColor(ColorCache color) {
        this.color = color;
        return this;
    }
    
    public void setHeight(double height) {
        if(this.wrapped instanceof Plane) {
            if(this.wrapped instanceof Square) {
                Square square = (Square)this.wrapped;
                square.setSideLength(height,square.getHeightRatio());
            }
            else {
                Plane plane = (Plane)this.wrapped;
                double hheight = height/2d;
                plane.setRelativeMax(plane.getRelativeMax().x,hheight);
                plane.setRelativeMin(plane.getRelativeMin().x,-hheight);
            }
        } else if(this.wrapped instanceof Circle) {
            Circle circle = (Circle)this.wrapped;
            circle.setRadius(height/2d);
        }
    }
    
    public RenderShape setTexture(ResourceLocationAPI<?> texture) {
        return setTexture(new TextureWrapper().setTexture(texture));
    }
    
    public RenderShape setTexture(ResourceLocationAPI<?> texture, float alpha) {
        return setTexture(new TextureWrapper().setTexture(texture).setAlpha(alpha));
    }
    
    public RenderShape setTexture(TextureWrapper texture) {
        this.texture = texture;
        return this;
    }
    
    public void setWidth(double width) {
        if(this.wrapped instanceof Plane) {
            if(this.wrapped instanceof Square) {
                Square square = (Square)this.wrapped;
                square.setSideLength(width,square.getHeightRatio());
            }
            else {
                Plane plane = (Plane)this.wrapped;
                double wwidth = width/2d;
                plane.setRelativeMax(wwidth,plane.getRelativeMax().y);
                plane.setRelativeMin(wwidth,plane.getRelativeMin().y);
            }
        } else if(this.wrapped instanceof Circle) {
            Circle circle = (Circle)this.wrapped;
            circle.setRadius(width/2d);
        }
    }
    
    @Override
    public RenderShape setWrapped(Shape shape) {
        return (RenderShape)super.setWrapped(shape);
    }
}
