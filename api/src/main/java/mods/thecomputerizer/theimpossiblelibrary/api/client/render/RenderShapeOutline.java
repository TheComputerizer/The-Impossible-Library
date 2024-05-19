package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape3D;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused") @Getter @Setter
public class RenderShapeOutline extends RenderShape {
    
    public static RenderShapeOutline of(RenderShape shape) {
        return of(shape,1f);
    }
    
    public static RenderShapeOutline of(RenderShape shape, float width) {
        return new RenderShapeOutline(shape.getWrapped(),shape.getColor(),width);
    }
    
    private float lineWidth;
    
    public RenderShapeOutline(Shape wrapped, float lineWidth) {
        this(wrapped,WHITE,lineWidth);
    }
    
    public RenderShapeOutline(Shape wrapped, ColorCache color, float lineWidth) {
        super(wrapped,color);
        this.lineWidth = lineWidth;
    }
    
    public RenderShapeOutline copy() {
        return new RenderShapeOutline(this.wrapped.copy(),this.color,this.lineWidth);
    }
    
    public void draw(RenderContext ctx, Vector3d center) {
        ctx.prepareGradient(this.color);
        if(this.wrapped instanceof Shape2D) ctx.drawOutline((Shape2D)this.wrapped,this.lineWidth);
        else if(this.wrapped instanceof Shape3D) ctx.drawOutline((Shape3D)this.wrapped,this.lineWidth);
        ctx.getRenderer().enableTexture();
        ctx.getRenderer().disableBlend();
    }
}
