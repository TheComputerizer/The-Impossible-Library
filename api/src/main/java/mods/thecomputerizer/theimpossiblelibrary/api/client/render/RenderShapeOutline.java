package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape3D;
import org.joml.Vector3d;

@SuppressWarnings("unused") @Getter @Setter
public class RenderShapeOutline extends RenderShape {
    
    public static RenderShapeOutline of(RenderShape shape) {
        return new RenderShapeOutline(shape.getWrapped(),shape.getColor(),1f);
    }
    
    public static RenderShapeOutline of(RenderShape shape, float width) {
        return new RenderShapeOutline(shape.getWrapped(),shape.getColor(),width);
    }
    
    private float lineWidth;
    
    public RenderShapeOutline(Shape wrapped, ColorCache color, float lineWidth) {
        super(wrapped,color);
        this.lineWidth = lineWidth;
    }
    
    public RenderShapeOutline copy() {
        return new RenderShapeOutline(this.wrapped.copy(),this.color,this.lineWidth);
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        if(this.wrapped instanceof Shape2D) ctx.drawOutline((Shape2D)this.wrapped,this.lineWidth,this.color);
        else if(this.wrapped instanceof Shape3D) ctx.drawOutline((Shape3D)this.wrapped,this.lineWidth,this.color);
    }
}
