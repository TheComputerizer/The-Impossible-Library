package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box.INFINITE;

@SuppressWarnings("unused") @Getter
public final class RenderScale {
    
    private double width;
    private double height;
    private double scaleX;
    private double scaleY;
    @Setter private double scaleZ = 1d;
    private double modScaleX = 1d;
    private double modScaleY = 1d;
    private double modScaleZ = 1d;
    private double transformX;
    private double transformY;
    private double transformZ;
    @Setter private Box renderBounds = INFINITE;
    
    RenderScale(MinecraftWindow window) {
        updateResolution(window.getWidth(),window.getHeight());
    }
    
    public VertexWrapper apply(VertexWrapper buffer, Vector3d center, double x, double y, double z) {
        return buffer.pos(applyX(center.x,x),applyY(center.y,y),applyZ(center.z,z));
    }
    
    double applyX(double centerX, double x) {
        x = Math.max(this.renderBounds.min.x,Math.min(x,this.renderBounds.max.x));
        x = 2d*(((x*this.modScaleX)+centerX+this.transformX)/this.scaleX);
        return x;
    }
    
    double applyY(double centerY, double y) {
        y = Math.max(this.renderBounds.min.y,Math.min(y,this.renderBounds.max.y));
        y = 2d*(((y*this.modScaleY)+centerY+this.transformY)/this.scaleY);
        return y;
    }
    
    double applyZ(double centerZ, double z) {
        return 0d;
    }
    
    public boolean canDraw(Vector3d center) {
        return this.renderBounds.isInside(center);
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
    
    public void updateResolution(double width, double height) {
        this.width = width;
        this.height = height;
        this.scaleX = 1d/(width/2d);
        this.scaleY = 1d/(height/2d);
    }
}
