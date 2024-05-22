package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

@SuppressWarnings("unused") @Getter @Setter
public abstract class Widget {
    
    protected Widget parent;
    protected double height;
    protected double width;
    protected double x;
    protected double y;
    
    protected Widget() {
        this(null);
    }
    
    protected Widget(Widget parent) {
        this.parent = parent;
    }
    
    public abstract Widget copy();
    
    public abstract void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY);
    
    public double getBottom(Vector3d center) {
        return getCenter(center).y-(getHeight()/2d);
    }
    
    public Vector3d getCenter(Vector3d parentCenter) {
        return this.x!=0d || this.y!=0d ?
                new Vector3d(parentCenter.x+this.x,parentCenter.y+this.y,parentCenter.z) : parentCenter;
    }
    
    public double getLeft(Vector3d center) {
        return getCenter(center).x-(getWidth()/2d);
    }
    
    public double getRight(Vector3d center) {
        return getCenter(center).x+(getWidth()/2d);
    }
    
    public double getTop(Vector3d center) {
        return getCenter(center).y+(getHeight()/2d);
    }
    public abstract void onResolutionUpdated(MinecraftWindow window);
}