package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShapeOutline;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Shape;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused")
public class OutlineWidget extends ShapeWidget {
    
    public static OutlineWidget of(ShapeWidget widget) {
        return new OutlineWidget(RenderShapeOutline.of(widget.getWrapped()),widget.x,widget.y);
    }
    
    public static OutlineWidget of(ShapeWidget widget, float lineWidth) {
        return new OutlineWidget(RenderShapeOutline.of(widget.getWrapped(),lineWidth),widget.x,widget.y);
    }
    
    public static OutlineWidget from(Shape shape) {
        return from(shape,0d,0d,WHITE,2f);
    }
    
    public static OutlineWidget from(Shape shape, double x, double y) {
        return new OutlineWidget(new RenderShapeOutline(shape,WHITE,2f),x,y);
    }
    
    public static OutlineWidget from(Shape shape, ColorCache color) {
        return from(shape,0d,0d,color,2f);
    }
    
    public static OutlineWidget from(Shape shape, double x, double y, ColorCache color) {
        return from(shape,x,y,color,2f);
    }
    
    public static OutlineWidget from(Shape shape, double x, double y, ColorCache color, float lineWidth) {
        return new OutlineWidget(new RenderShapeOutline(shape,color,lineWidth),x,y);
    }
    
    public OutlineWidget(RenderShapeOutline shape, double x, double y) {
        super(shape,x,y);
    }
    
    @Override public OutlineWidget copy() {
        OutlineWidget copy = new OutlineWidget((RenderShapeOutline)this.shape,this.x,this.y);
        copy.height = this.height;
        copy.width = this.width;
        return copy;
    }
}
