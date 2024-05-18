package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderScale;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box.INFINITE;

public abstract class BoundedWidgetGroup extends WidgetGroup {
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        RenderScale scale = ctx.getScale();
        scale.setRenderBounds(new Box(center,getWidth()/2d,getHeight()/2d));
        super.draw(ctx,center,mouseX,mouseY);
        scale.setRenderBounds(INFINITE);
    }
}