package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import java.util.Objects;

/**
 Planes always assume (0,0,0) as the center unless implemented otherwise in an extension class
 */
@SuppressWarnings("unused") @Getter
public class Plane extends Shape2D {
    
    public static Plane getBounded(Vector3 direction, double width, double height, double heightRatio) {
        width = (width*(Math.min(heightRatio,1d)))/2d;
        height = (height*(Math.min(1d/heightRatio,1d)))/2d;
        Vector2 min = new Vector2(-width,-height);
        Vector2 max = new Vector2(width, height);
        return new Plane(direction,min,max);
    }
    
    public static Plane getBoundedAxis(Axis axis, double width, double height, double heightRatio) {
        return getBounded(axis.getDirection(),width,height,heightRatio);
    }
    
    public static Plane[] getOutlinePlanes(Plane outer, Plane inner) {
        return new Plane[] {
                new Plane(outer.direction,outer.relativeMin,new Vector2(inner.relativeMin.dX(),outer.relativeMax.dY())),
                new Plane(outer.direction,new Vector2(inner.relativeMin.dX(),outer.relativeMin.dY()),
                          new Vector2(inner.relativeMax.dX(),inner.relativeMin.dY())),
                new Plane(outer.direction,new Vector2(inner.relativeMin.dX(),inner.relativeMax.dY()),
                          new Vector2(inner.relativeMax.dX(),outer.relativeMax.dY())),
                new Plane(outer.direction,new Vector2(inner.relativeMax.dX(),outer.relativeMin.dY()),outer.relativeMax)
        };
    }
    
    protected Vector3 worldMin;
    protected Vector3 worldMax;
    protected Vector2 relativeMin;
    protected Vector2 relativeMax;
    protected double width;
    protected double height;
    
    /**
     See ShapeHelper for alternative construction methods
     */
    public Plane(Vector3 direction, Vector2 corner, Vector2 oppositeCorner) {
        super(direction);
        this.relativeMin = new Vector2(Math.min(corner.dX(),oppositeCorner.dX()),Math.min(corner.dY(),oppositeCorner.dY()));
        this.relativeMax = new Vector2(Math.max(corner.dX(),oppositeCorner.dX()),Math.max(corner.dY(),oppositeCorner.dY()));
        calculateActuals();
        calculateSize();
    }
    
    protected void calculateActuals() {
        this.worldMin = getWorldCoordinate(this.relativeMin);
        this.worldMax = getWorldCoordinate(this.relativeMax);
    }
    
    protected void calculateSize() {
        this.width = Math.abs(this.relativeMax.dX()-this.relativeMin.dX());
        this.height = Math.abs(this.relativeMax.dY()-this.relativeMin.dY());
    }
    
    @Override public boolean checkToleranceBounds(Vector3 center, Box bounds) {
        return bounds.expand(getWidth()/2d,getHeight()/2d,0d).isInside(getCenter(center));
    }
    
    @Override public Plane copy() {
        return getScaled(1d,1d);
    }
    
    @Override public boolean equals(Object other) {
        if(this==other) return true;
        if(Objects.isNull(other)) return false;
        if(other.getClass()==Plane.class) {
            Plane plane = (Plane)other;
            return sameDirection(plane) && Misc.equalsNullable(this.relativeMin,plane.relativeMin) &&
                   Misc.equalsNullable(this.relativeMax,plane.relativeMax);
        }
        return false;
    }
    
    @Override public double getDepth() {
        return 0d;
    }
    
    @Override public double getBoundedX(double x, double y, double z) {
        return Math.max(this.relativeMin.dX(),Math.min(this.relativeMax.dX(),x));
    }
    
    @Override public double getBoundedY(double x, double y, double z) {
        return Math.max(this.relativeMin.dY(),Math.min(this.relativeMax.dY(),y));
    }
    
    @Override public Plane getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    @Override public Plane getScaled(Vector2 scale) {
        return getScaled(scale.dX(),scale.dY());
    }
    
    @Override public Plane getScaled(double scaleX, double scaleY) {
        if(scaleX<=0d) scaleX = 1d;
        if(scaleY<=0d) scaleY = 1d;
        return new Plane(new Vector3(this.direction),this.relativeMin.mulScalar(scaleX,new Vector2()),
                         this.relativeMax.mulScalar(scaleY,new Vector2()));
    }
    
    @Override public Plane getScaled(Vector3 scale) {
        return getScaled(scale.dX(),scale.dY());
    }
    
    @Override public Plane getScaled(double scaleX, double scaleY, double scaleZ) {
        return getScaled(scaleX,scaleY);
    }
    
    @Override public VectorSupplier2D getVectorSupplier(Box bounds) {
        return VectorStreams.get2D(bounds.getBoundedXY(this.relativeMin),
                                   bounds.getBoundedXY(this.relativeMax.dX(),this.relativeMin.dY()),
                                   bounds.getBoundedXY(this.relativeMax),
                                   bounds.getBoundedXY(this.relativeMin.dX(),this.relativeMax.dY()));
    }
    
    @Override public boolean isInsideRelative(Vector2 pos) {
        return pos.dX()>=this.relativeMin.dX() && pos.dX()<=this.relativeMax.dX() &&
               pos.dY()>=this.relativeMin.dY() && pos.dY()<=this.relativeMax.dY();
    }
    
    @Override public Vector2 random2D() {
        return VectorHelper.randomD(this.relativeMin,this.relativeMax);
    }
    
    @Override public Vector3 random3D() {
        return new Vector3(random2D(),0d);
    }
    
    public void setRelativeMax(double x, double y) {
        setRelativeMax(new Vector2(x,y));
    }
    
    public void setRelativeMax(Vector2 max) {
        this.relativeMax = max;
        this.worldMax = getWorldCoordinate(max);
        calculateSize();
    }
    
    public void setRelativeMin(double x, double y) {
        setRelativeMin(new Vector2(x,y));
    }
    
    public void setRelativeMin(Vector2 min) {
        this.relativeMin = min;
        this.worldMin = getWorldCoordinate(min);
        calculateSize();
    }
    
    public void setScales(double scaledWidth, double scaledHeight) {
        setScaleWidth(scaledWidth);
        setScaleHeight(scaledHeight);
    }
    
    public void setScaleHeight(double scale) {
        double height = getHeight()/2d;
        double center = this.relativeMin.dY()+height;
        setRelativeMin(this.relativeMin.dX(),center-(height*scale));
        setRelativeMax(this.relativeMax.dX(),center+(height*scale));
    }
    
    public void setScaleWidth(double scale) {
        double width = getWidth()/2d;
        double center = this.relativeMin.dX()+width;
        setRelativeMin(center-(width*scale),this.relativeMin.dY());
        setRelativeMax(center+(width*scale),this.relativeMax.dY());
    }
}