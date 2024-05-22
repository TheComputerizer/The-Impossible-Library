package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box.INFINITE;

@SuppressWarnings("unused")
public abstract class BoundedWidgetGroup extends WidgetGroup {
    
    @Override public abstract BoundedWidgetGroup copy();
    
    @Override public void drawWidget(RenderContext ctx, Widget widget, int index, Vector3d center, double mouseX,
            double mouseY) {
        ctx.getScale().setRenderBounds(getRenderBounds(widget,index,center));
        super.drawWidget(ctx,widget,index,center,mouseX,mouseY);
        ctx.getScale().setRenderBounds(INFINITE);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double mouseX, double mouseY) {
        return isBounded(null,0,getCenter(VectorHelper.zero3D()),mouseX,mouseY) ?
                super.getHoverLines(mouseX,mouseY) : Collections.emptyList();
    }
    
    protected Box getRenderBounds(Widget widget, int index, Vector3d center) {
        return ShapeHelper.box(getCenter(center),getWidth(),getHeight());
    }
    
    public boolean isBounded(Widget widget, int index, Vector3d center, double x, double y) {
        return getRenderBounds(widget,index,center).isInside(x,y,0d);
    }
    
    public boolean isBounded(Widget widget, int index, Vector3d center, Vector3d pos) {
        return getRenderBounds(widget,index,center).isInside(pos);
    }
    
    @Override public boolean onClicked(double mouseX, double mouseY, boolean leftClick) {
        return isBounded(null,0,getCenter(VectorHelper.zero3D()),mouseX,mouseY) &&
                super.onClicked(mouseX,mouseY,leftClick);
    }
}