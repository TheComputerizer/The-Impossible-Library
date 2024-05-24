package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box.INFINITE;

@SuppressWarnings("unused") @Getter
public final class RenderScale {
    
    private double displayWidth;
    private double displayHeight;
    private double displayRatio;
    private double displayScaleX;
    private double displayScaleY;
    private double screenWidth;
    private double screenHeight;
    private double screenRatio;
    private double screenScaleX;
    private double screenScaleY;
    @Setter private double scaleZ = 1d;
    private double modScaleX = 1d;
    private double modScaleY = 1d;
    private double modScaleZ = 1d;
    private double transformX;
    private double transformY;
    private double transformZ;
    @Setter private Box renderBounds = INFINITE;
    
    RenderScale(MinecraftWindow window) {
        updateResolution(window.getWidth(),window.getHeight(),window.getDisplayWidth(),window.getDisplayHeight());
    }
    
    public VertexWrapper applyForScreen(VertexWrapper buffer, Vector3d center, double x, double y, double z) {
        return buffer.pos(applyXForScreen(center.x,x),applyYForScreen(center.y,y),applyZForScreen(center.z,z));
    }
    
    double applyXForScreen(double centerX, double x) {
        x = Math.max(this.renderBounds.min.x,Math.min(centerX+x,this.renderBounds.max.x));
        x = ((x*this.modScaleX)+this.transformX+1d)/this.screenScaleX;
        return x;
    }
    
    double applyYForScreen(double centerY, double y) {
        y = Math.max(this.renderBounds.min.y,Math.min(centerY+y,this.renderBounds.max.y));
        y = this.screenHeight-(((y*this.modScaleY)+this.transformY+1d)/this.screenScaleY);
        return y;
    }
    
    double applyZForScreen(double centerZ, double z) {
        return centerZ+z;
    }
    
    public boolean canDraw(Vector3d center) {
        return this.renderBounds.isInside(center);
    }
    
    public double getSmallerDimensionScale() {
        return this.screenWidth>=this.screenHeight ? this.screenRatio : this.screenWidth/this.screenHeight;
    }
    
    public void modScales(double x, double y, double z) {
        this.modScaleX*=x;
        this.modScaleY*=y;
        this.modScaleZ*=z;
    }
    
    public void modTransforms(double x, double y, double z) {
        this.transformX+=(x*this.modScaleX);
        this.transformY+=(y*this.modScaleY);
        this.transformZ+=(z*this.modScaleZ);
    }
    
    public double normalizeDisplayX(Number x) {
        return (x.doubleValue()*this.displayScaleX)-1d;
    }
    
    public double normalizeDisplayY(Number y) {
        return 1d-(y.doubleValue()*this.displayScaleY);
    }
    
    public double normalizeX(Number x) {
        return (x.doubleValue()*this.screenScaleX)-1d;
    }
    
    public double normalizeY(Number y) {
        return 1d-(y.doubleValue()*this.screenScaleY);
    }
    
    public void updateResolution(double screenWidth, double screenHeight, double displayWidth, double displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.displayRatio = displayHeight/displayWidth;
        this.displayScaleX = 1d/(displayWidth/2d);
        this.displayScaleY = 1d/(displayHeight/2d);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.screenRatio = screenHeight/screenWidth;
        this.screenScaleX = 1d/(screenWidth/2d);
        this.screenScaleY = 1d/(screenHeight/2d);
    }
}
