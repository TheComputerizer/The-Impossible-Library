package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import org.joml.Vector3d;

import java.util.Objects;

@SuppressWarnings("unused") @Setter
public abstract class Widget {
    
    @Getter protected Widget parent;
    @Getter protected double height;
    @Getter protected double width;
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
    
    public double getBottom() {
        return getY()-(getHeight()/2d);
    }
    
    public Vector3d getCenter(double z) {
        return new Vector3d(getX(),getY(),z);
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
    
    public abstract void onResolutionUpdated(MinecraftWindow window);
}