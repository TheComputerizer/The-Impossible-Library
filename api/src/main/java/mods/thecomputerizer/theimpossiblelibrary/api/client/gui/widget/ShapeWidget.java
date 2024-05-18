package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderShape;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import org.joml.Vector3d;

@SuppressWarnings("unused") @Getter @Setter
public class ShapeWidget extends Widget implements Wrapped<RenderShape> {
    
    protected RenderShape shape;
    
    public ShapeWidget(RenderShape shape, double x, double y) {
        this.shape = shape;
        this.x = MathHelper.clamp(x,-1d,1d);
        this.y = MathHelper.clamp(y,-1d,1d);
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        this.shape.draw(ctx,center);
    }
    
    @Override public double getHeight() {
        return this.shape.getHeight();
    }
    
    @Override public double getWidth() {
        return this.shape.getWidth();
    }
    
    @Override public RenderShape getWrapped() {
        return this.shape;
    }
    
    public boolean isInside(double x, double y, double z) {
        return this.shape.getWrapped().isInside(new Vector3d(x,y,z));
    }
    
    public void setColor(ColorCache color) {
        this.shape.setColor(color);
    }
    
    public void setTexture(TextureWrapper texture) {
        this.shape.setTexture(texture);
    }
}
