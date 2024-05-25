package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.GLAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderScale;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.joml.Vector3d;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("unused")
public abstract class BoundedWidgetGroup extends WidgetGroup {
    
    @Override public abstract BoundedWidgetGroup copy();
    
    @Override protected boolean drawHoverable(RenderContext ctx, Hoverable hoverable, Vector3d center, double mouseX, double mouseY) {
        return super.drawHoverable(ctx,hoverable,center,mouseX,mouseY);
    }
    
    @Override public void drawWidget(RenderContext ctx, Widget widget, Vector3d center, double mouseX, double mouseY) {
        Box previousBounds = ctx.getScale().getRenderBounds();
        Box box = getRenderBounds(center);
        RenderScale scale = ctx.getScale();
        GLAPI gl = ctx.getRenderer().getGLAPI();
        scale.setRenderBounds(box);
        gl.enable(gl.scissorTest());
        ctx.scissorScaled(getLeft(),-getBottom(),box.getWidth(),box.getHeight()+1d);
        super.drawWidget(ctx,widget,center,mouseX,mouseY);
        gl.disable(gl.scissorTest());
        scale.setRenderBounds(previousBounds);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double mouseX, double mouseY) {
        return isBounded(VectorHelper.zero3D(),mouseX,mouseY) ?
                super.getHoverLines(mouseX,mouseY) : Collections.emptyList();
    }
    
    protected Box getRenderBounds(Vector3d center) {
        return ShapeHelper.box(getCenter(0d).add(center),getWidth(),getHeight());
    }
    
    public boolean isBounded(Vector3d center, double x, double y) {
        return getRenderBounds(center).isInside(x,y,0d);
    }
    
    public boolean isBounded(Vector3d center, Vector3d pos) {
        return getRenderBounds(center).isInside(pos);
    }
    
    @Override public boolean onClicked(double mouseX, double mouseY, boolean leftClick) {
        return isBounded(VectorHelper.zero3D(),mouseX,mouseY) && super.onClicked(mouseX,mouseY,leftClick);
    }
}