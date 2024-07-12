package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

import java.util.Objects;

@Setter @SuppressWarnings("unused")
public abstract class Widget {
    
    @Getter protected double height;
    @Getter protected Widget parent;
    protected boolean visible;
    @Getter protected double width;
    protected double x;
    protected double y;
    
    protected Widget() {
        this(null);
    }
    
    protected Widget(Widget parent) {
        this.parent = parent;
        this.visible = true;
    }
    
    public abstract Widget copy();
    
    protected void copyBasic(Widget other) {
        this.height = other.height;
        this.parent = other.parent;
        this.visible = other.visible;
        this.width = other.width;
        this.x = other.x;
        this.y = other.y;
    }
    
    public boolean canDraw() {
        return isVisible();
    }
    
    public abstract void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY);
    
    public double getBottom() {
        return getY()-(getHeight()/2d);
    }
    
    public Vector3d getCenter() {
        return new Vector3d(getX(),getY(),0d);
    }
    
    public Vector3d getCenter(double z) {
        return new Vector3d(getX(),getY(),z);
    }
    
    public Vector3d getCenter(Vector3d center) {
        return new Vector3d(getX()+center.x,getY()+center.y,center.z);
    }
    
    public double getLeft() {
        return getX()-(getWidth()/2d);
    }
    
    public double getRight() {
        return getX()+(getWidth()/2d);
    }
    
    public double getTop() {
        return getY()+(getHeight()/2d);
    }
    
    public double getX() {
        return Objects.nonNull(this.parent) ? this.x+this.parent.getX() : this.x;
    }
    
    public double getY() {
        return Objects.nonNull(this.parent) ? this.y+this.parent.getY() : this.y;
    }
    
    public boolean isVisible() {
        return this.visible && (Objects.isNull(this.parent) || this.parent.isVisible());
    }
    
    public abstract void onResolutionUpdated(MinecraftWindow window);
    
    public void onScreenClosed() {}
    
    public void onScreenOpened() {}
}