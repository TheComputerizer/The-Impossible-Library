package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import org.joml.Vector2d;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper.MAX_2D;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper.MIN_2D;

@SuppressWarnings("unused") @Getter
public class Plane extends Shape2D {
    
    public static Plane getFromAxis(Axis axis, double width, double height) {
        Plane plane = getFromAxis(axis);
        plane.setRelativeMin(-width/2d,-height/2d);
        plane.setRelativeMax(width/2d,height/2d);
        return plane;
    }
    
    public static Plane getFromAxis(Axis axis) {
        switch(axis) {
            default:
            case X: return new Plane(new Vector3d(1d,0d,0d));
            case Y: return new Plane(new Vector3d(0d,1d,0d));
            case Z: return new Plane(new Vector3d(0d,0d,1d));
        }
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
        this(direction,MIN_2D,MAX_2D);
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
    
    public Plane getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    public Plane getScaled(double scaleX, double scaleY) {
        if(scaleX<=0d) scaleX = 1d;
        if(scaleY<=0d) scaleY = 1d;
        return new Plane(this.direction,this.relativeMin.mul(scaleX,new Vector2d()),this.relativeMax.mul(scaleY,new Vector2d()));
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