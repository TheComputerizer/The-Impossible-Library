package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

public abstract class BoundedWidgetGroup extends WidgetGroup {
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        //RenderScale scale = ctx.getScale();
        //scale.setRenderBounds(ShapeHelper.box(center,getWidth(),getHeight()));
        super.draw(ctx,center,mouseX,mouseY);
        //scale.setRenderBounds(INFINITE);
    }
}