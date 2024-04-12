package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.Objects;

import static java.lang.Double.MIN_VALUE;

public class Box {

    public static final Box INFINITE = new Box(MIN_VALUE, MIN_VALUE, MIN_VALUE,
            Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    public static final Box ZERO = new Box(0d,0d,0d,0d,0d,0d);

    public final Vector3d min;
    public final Vector3d max;
    public final Vector3d center;

    public Box(Vector3d v1, Vector3d v2) {
        this(v1.x,v1.y,v1.z,v2.x,v2.y,v2.z);
    }

    public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.min = new Vector3d(Math.min(x1,x2),Math.min(y1,y2),Math.min(z1,z2));
        this.max = new Vector3d(Math.max(x1,x2),Math.max(y1,y2),Math.max(z1,z2));
        this.center = VectorHelper.getCenter(this.min,this.max);
    }

    /**
     * Returns a new box with the smallest min values and the biggest max values from the input boxes.
     */
    public Box add(Box ... boxes) {
        if(Objects.isNull(boxes) || boxes.length==0) return this;
        Vector3d min = this.min.min(boxes[0].min,new Vector3d());
        Vector3d max = this.max.max(boxes[0].max,new Vector3d());
        for(int i=1;i<boxes.length;i++) {
            min = min.min(boxes[i].min);
            max = max.max(boxes[i].max);
        }
        return new Box(min,max);
    }

    /**
     * Multiplies by 1/d.
     * If d is 0, MIN_VALUE, or NaN INFINITE will be returned.
     * If d is NEGATIVE_INFINITY, POSITIVE_INFINITY, or MAX_VALUE ZERO will be returned.
     */
    public Box div(double d) {
        return mul(1/d,1/d,1/d);
    }

    /**
     * Multiplies by 1/x, 1/y, and 1/z respectively.
     * If x, y, or z are 0 or NaN INFINITE will be returned
     * If x, y, or z are NEGATIVE_INFINITY, POSITIVE_INFINITY, MAX_VALUE, or MIN_VALUE ZERO will be returned
     */
    public Box div(double x, double y, double z) {
        return Double.isNaN(x) || x==0 || Double.isNaN(y) || y==0 || Double.isNaN(z) || z==0 ? INFINITE :
                (isFiniteAndNotMaxed(x,y,z)  ? mul(1/x,1/y,1/z) : ZERO);
    }

    public Box expand(double d) {
        return expand(d,d,d);
    }

    /**
     * Expand both min and max values by their respective inputs.
     * Expansions less than or equal to the negative radius of that side will return ZERO.
     */
    public Box expand(double x, double y, double z) {
        if(x==0 && y==0 && z==0) return this;
        double xRad = radiusX();
        double yRad = radiusY();
        double zRad = radiusZ();
        if(xRad+x<=0 || yRad+y<=0 || zRad+z<=0) return ZERO;
        return new Box(this.min.sub(x,y,z,new Vector3d()),this.max.add(x,y,z,new Vector3d()));
    }

    private boolean isFiniteAndNotMaxed(double d) {
        return Double.isFinite(d) && Math.abs(d)<Double.MAX_VALUE;
    }

    private boolean isFiniteAndNotMaxed(double x, double y, double z) {
        return isFiniteAndNotMaxed(x) && isFiniteAndNotMaxed(y) && isFiniteAndNotMaxed(z);
    }

    public boolean isInside(BlockPosAPI<?> pos) {
        return isInside(pos.x(),pos.y(),pos.z());
    }

    public boolean isInside(Vector3d pos) {
        return isInside(pos.x,pos.y,pos.z);
    }

    public boolean isInside(Vector3i pos) {
        return isInside(pos.x,pos.y,pos.z);
    }

    public boolean isInside(double x, double y, double z) {
        return x>=this.min.x && x<=this.max.x && y>=this.min.y && y<=this.max.y && z>=this.min.z && z<=this.max.z;
    }

    public double lengthX() {
        return this.max.x-this.min.x;
    }

    public double lengthY() {
        return this.max.y-this.min.y;
    }

    public double lengthZ() {
        return this.max.z-this.min.z;
    }

    /**
     * Multiplies each radius by d.
     * If d is 0 or NaN ZERO will be returned.
     * If d is NEGATIVE_INFINITY, POSITIVE_INFINITY, MAX_VALUE, or MIN_VALUE INFINITE will be returned.
     */
    public Box mul(double d) {
        return mul(d,d,d);
    }

    /**
     * Multiplies each radius by the respective x, y, z input.
     * If x, y, or z are 0 or NaN ZERO will be returned.
     * If x, y, or z are NEGATIVE_INFINITY, POSITIVE_INFINITY, MAX_VALUE, or MIN_VALUE INFINITE will be returned.
     */
    public Box mul(double x, double y, double z) {
        if(Double.isNaN(x) || x==0 || Double.isNaN(y) || y==0 || Double.isNaN(z) || z==0) return ZERO;
        if(!isFiniteAndNotMaxed(x,y,z)) return INFINITE;
        if(x==1 && y==1 && z==1) return this;
        double xRad = radiusX();
        double yRad = radiusY();
        double zRad = radiusZ();
        return expand((xRad*x)-xRad,(yRad*y)-yRad,(zRad*z)-zRad);
    }

    public Box offset(double d) {
        return offset(d,d,d);
    }

    public Box offset(double x, double y, double z) {
        return x==0 && y==0 && z==0 ? this :
                new Box(this.min.add(x,y,z,new Vector3d()),this.max.add(x,y,z,new Vector3d()));
    }

    public double radiusX() {
        return lengthX()/2d;
    }

    public double radiusY() {
        return lengthY()/2d;
    }

    public double radiusZ() {
        return lengthZ()/2d;
    }

    public Box shrink(double d) {
        return expand(-d,-d,-d);
    }

    public Box shrink(double x, double y, double z) {
        return expand(-x,-y,-z);
    }
}