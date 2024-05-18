package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;
import org.joml.Vector3d;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused") @Getter @Setter
public class RenderShape extends AbstractWrapped<Shape> {
    
    private ColorCache color;
    private TextureWrapper texture;
    
    public RenderShape(Shape wrapped) {
        this(wrapped,WHITE);
    }
    
    public RenderShape(Shape wrapped, ColorCache color) {
        super(wrapped);
        this.color = color;
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        if(this.wrapped instanceof Plane) {
            Plane plane = (Plane)this.wrapped;
            if(Objects.nonNull(this.texture)) ctx.drawTexturedPlane(center,plane,this.texture);
            else ctx.drawColoredPlane(center,plane,this.color);
        } else if(this.wrapped instanceof Circle) ctx.drawColoredCircle(center,(Circle)this.wrapped,this.color);
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
}
