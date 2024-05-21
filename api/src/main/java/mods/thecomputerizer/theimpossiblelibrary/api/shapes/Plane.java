package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import org.joml.Vector2d;
import org.joml.Vector3d;

/**
 Planes always assume (0,0,0) as the center unless implemented otherwise in an extension class
 */
@SuppressWarnings("unused") @Getter
public class Plane extends Shape2D {
    
    public static Plane getBounded(Vector3d direction, double width, double height, double heightRatio) {
        width = (width*(Math.min(heightRatio,1d)))/2d;
        height = (height*(Math.min(1d/heightRatio,1d)))/2d;
        Vector2d min = new Vector2d(-width,-height);
        Vector2d max = new Vector2d(width,height);
        return new Plane(direction,min,max);
    }
    
    public static Plane getBoundedAxis(Axis axis, double width, double height, double heightRatio) {
        return getBounded(axis.getDirection(),width,height,heightRatio);
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
    
    /**
     See ShapeHelper for alternative construction methods
     */
    public Plane(Vector3d direction, Vector2d corner, Vector2d oppositeCorner) {
        super(direction);
        this.relativeMin = new Vector2d(Math.min(corner.x,oppositeCorner.x),Math.min(corner.y,oppositeCorner.y));
        this.relativeMax = new Vector2d(Math.max(corner.x,oppositeCorner.x),Math.max(corner.y,oppositeCorner.y));
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
    
    @Override public VectorSupplier2D getVectorSupplier() {
        return VectorStreams.get2D(this.relativeMin,new Vector2d(this.relativeMax.x,this.relativeMin.y),
                                   this.relativeMax,new Vector2d(this.relativeMin.x,this.relativeMax.y));
    }
    
    @Override public boolean isInsideRelative(Vector2d pos) {
        return pos.x>=this.relativeMin.x && pos.x<=this.relativeMax.x &&
               pos.y>=this.relativeMin.y && pos.y<=this.relativeMax.y;
    }
    
    @Override public Vector2d random2D() {
        return VectorHelper.randomD(this.relativeMin, this.relativeMax);
    }
    
    @Override public Vector3d random3D() {
        return new Vector3d(random2D(),0d);
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