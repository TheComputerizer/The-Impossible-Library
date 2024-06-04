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
    
    @Override public void drawWidget(RenderContext ctx, Widget widget, Vector3d center, double mouseX, double mouseY) {
        RenderScale scale = ctx.getScale();
        Box previousBounds = scale.getRenderBounds();
        Box bounds = getRenderBounds(center);
        scale.setRenderBounds(bounds);
        GLAPI gl = ctx.getRenderer().getGLAPI();
        int scissorTest = gl.scissorTest();
        gl.enable(scissorTest);
        ctx.scissorScaled(getLeft(),-getBottom(),bounds.getWidth(),bounds.getHeight()+1d);
        super.drawWidget(ctx,widget,center,mouseX,mouseY);
        gl.disable(scissorTest);
        scale.setRenderBounds(previousBounds);
    }
    
    @Override public Collection<TextAPI<?>> getHoverLines(double mouseX, double mouseY) {
        return isBounded(mouseX,mouseY) ? super.getHoverLines(mouseX,mouseY) : Collections.emptyList();
    }
    
    protected Box getRenderBounds(Vector3d center) {
        return ShapeHelper.box(getCenter(center),getWidth(),getHeight());
    }
    
    protected Box getRenderBounds(Vector3d center, Vector3d offset) {
        return getRenderBounds(center,offset.x,offset.y,offset.z);
    }
    
    protected Box getRenderBounds(Vector3d center, double offsetX, double offsetY, double offsetZ) {
        return ShapeHelper.box(getCenter(center).add(offsetX,offsetY,offsetZ),getWidth(),getHeight());
    }
    
    public boolean isBounded(double x, double y) {
        return isBounded(VectorHelper.zero3D(),x,y);
    }
    
    public boolean isBounded(Vector3d center, double x, double y) {
        return getRenderBounds(center).isInsideXY(x,y);
    }
    
    public boolean isBounded(Vector3d center, Vector3d pos) {
        return getRenderBounds(center).isInside(pos);
    }
    
    @Override public boolean onLeftClick(double mouseX, double mouseY) {
        return isBounded(mouseX,mouseY) && super.onLeftClick(mouseX,mouseY);
    }
    
    @Override public boolean onRightClick(double mouseX, double mouseY) {
        return isBounded(mouseX,mouseY) && super.onRightClick(mouseX,mouseY);
    }
}