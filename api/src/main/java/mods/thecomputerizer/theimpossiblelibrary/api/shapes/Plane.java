package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import org.joml.Vector2d;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.VectorHelper.inf2D;
import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.VectorHelper.negInf2D;

/**
 Planes always assume (0,0,0) as the center unless implemented otherwise in an extension class
 */
@SuppressWarnings("unused") @Getter
public class Plane extends Shape2D {
    
    public static Plane getBounded(Vector3d direction, double width, double height) {
        Vector2d min = new Vector2d(-width/2d,-height/2d);
        Vector2d max = new Vector2d(width/2d,height/2d);
        return new Plane(direction,min,max);
    }
    
    public static Plane getBoundedAxis(Axis axis, double width, double height) {
        return getBounded(axis.getDirection(),width,height);
    }
    
    public static Plane[] getOutlinePlanes(Plane outer, Plane inner) {
        return new Plane[] {
                new Plane(outer.direction,outer.relativeMin,new Vector2d(inner.relativeMin.x,outer.relativeMax.y)),
                new Plane(outer.direction,new Vector2d(inner.relativeMin.x,outer.relativeMin.y),
                          new Vector2d(inner.relativeMax.x,inner.relativeMin.y)),
                new Plane(outer.direction,new Vector2d(inner.relativeMin.x,inner.relativeMax.y),
                          new Vector2d(inner.relativeMax.x,outer.relativeMax.y)),
                new Plane(outer.direction,new Vector2d(inner.relativeMax.x,outer.relativeMin.y),outer.relativeMax)
        };
    }
    
    protected Vector3d worldMin;
    protected Vector3d worldMax;
    protected Vector2d relativeMin;
    protected Vector2d relativeMax;
    protected double width;
    protected double height;
    
    public Plane(Vector3d min, Vector3d max) {
        this(max.sub(min));
    }
    
    public Plane(Vector3d direction) {
        this(direction,negInf2D(),inf2D());
    }
    
    public Plane(Vector3d direction, Vector2d min, Vector2d max) {
        super(direction);
        this.relativeMin = min;
        this.relativeMax = max;
        calculateActuals();
        calculateSize();
    }
    
    protected void calculateActuals() {
        this.worldMin = getWorldCoordinate(this.relativeMin);
        this.worldMax = getWorldCoordinate(this.relativeMax);
    }
    
    protected void calculateSize() {
        this.width = Math.abs(this.relativeMax.x-this.relativeMin.x);
        this.height = Math.abs(this.relativeMax.y-this.relativeMin.y);
    }
    
    @Override public Plane copy() {
        return getScaled(1d,1d);
    }
    
    @Override public Plane getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    @Override public Plane getScaled(Vector2d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Plane getScaled(double scaleX, double scaleY) {
        if(scaleX<=0d) scaleX = 1d;
        if(scaleY<=0d) scaleY = 1d;
        return new Plane(new Vector3d(this.direction),this.relativeMin.mul(scaleX,new Vector2d()),
                         this.relativeMax.mul(scaleY,new Vector2d()));
    }
    
    @Override public Plane getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Plane getScaled(double scaleX, double scaleY, double scaleZ) {
        return getScaled(scaleX,scaleY);
    }
    
    @Override public boolean isInsideRelative(Vector2d pos) {
        return pos.x>=this.relativeMin.x && pos.x<=this.relativeMax.x &&
               pos.y>=this.relativeMin.y && pos.y<=this.relativeMax.y;
    }
    
    public void setRelativeMax(double x, double y) {
        setRelativeMax(new Vector2d(x,y));
    }
    
    public void setRelativeMax(Vector2d max) {
        this.relativeMax = max;
        this.worldMax = getWorldCoordinate(max);
        calculateSize();
    }
    
    public void setRelativeMin(double x, double y) {
        setRelativeMin(new Vector2d(x,y));
    }
    
    public void setRelativeMin(Vector2d min) {
        this.relativeMin = min;
        this.worldMin = getWorldCoordinate(min);
        calculateSize();
    }
    
    @Override public double getDepth() {
        return 0d;
    }
}