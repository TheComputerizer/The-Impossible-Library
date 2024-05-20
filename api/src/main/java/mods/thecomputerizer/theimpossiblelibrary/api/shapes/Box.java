package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.Objects;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

@SuppressWarnings("unused")
public class Box extends Shape3D { //TODO Finish edge cases for weird doubles

    public static final Box INFINITE = new Box(NEGATIVE_INFINITY,NEGATIVE_INFINITY,NEGATIVE_INFINITY,
                                               POSITIVE_INFINITY,POSITIVE_INFINITY,POSITIVE_INFINITY) {
        @Override public boolean isInside(BlockPosAPI<?> pos) { return true; }
        @Override public boolean isInside(Vector3d pos) { return true; }
        @Override public boolean isInside(Vector3i pos) { return true; }
        @Override public boolean isInside(double x, double y, double z) { return true; }
    };
    public static final Box ZERO = new Box(0d,0d,0d,0d,0d,0d) {
        @Override public boolean isInside(BlockPosAPI<?> pos) { return false; }
        @Override public boolean isInside(Vector3d pos) { return false; }
        @Override public boolean isInside(Vector3i pos) { return false; }
        @Override public boolean isInside(double x, double y, double z) { return false; }
    };

    public final Vector3d min;
    public final Vector3d max;
    public final Vector3d center;
    
    public Box(Vector3d center, double radius) {
        this(center,radius,radius,radius);
    }
    
    public Box(Vector3d center, double radiusH, double radiusV) {
        this(center,radiusH,radiusV,radiusH);
    }
    
    public Box(Vector3d center, double radiusX, double radiusY, double radiusZ) {
        this(center.x-radiusX,center.y-radiusY,center.z-radiusZ,
             center.x+radiusX,center.y+radiusY,center.z+radiusZ);
    }

    public Box(Vector3d v1, Vector3d v2) {
        this(v1.x,v1.y,v1.z,v2.x,v2.y,v2.z);
    }

    public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.min = new Vector3d(Math.min(x1,x2),Math.min(y1,y2),Math.min(z1,z2));
        this.max = new Vector3d(Math.max(x1,x2),Math.max(y1,y2),Math.max(z1,z2));
        this.center = VectorHelper.getCenter(this.min, this.max);
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
    
    @Override public Box copy() {
        return mul(1d,1d,1d);
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
    
    @Override public double getDepth() {
        return Math.abs(this.max.z-this.min.z);
    }
    
    @Override public double getHeight() {
        return Math.abs(this.max.y-this.min.y);
    }
    
    @Override public Box getScaled(double scale) {
        return mul(scale,scale,scale);
    }
    
    @Override public Box getScaled(Vector2d scale) {
        return mul(scale.x,scale.y,scale.x);
    }
    
    @Override public Box getScaled(double scaleH, double scaleV) {
        return mul(scaleH,scaleV,scaleH);
    }
    
    @Override public Box getScaled(Vector3d scale) {
        return mul(scale.x,scale.y,scale.z);
    }
    
    @Override public Box getScaled(double scaleX, double scaleY, double scaleZ) {
        return mul(scaleX,scaleY,scaleZ);
    }
    
    @Override public VectorSupplier3D getVectorSupplier() {
        return VectorStreams.get3D(
                new Vector3d(this.min),
                new Vector3d(this.min.x,this.min.y,this.max.z),
                new Vector3d(this.min.x,this.max.y,this.max.z),
                new Vector3d(this.min.x,this.max.y,this.min.z),
                new Vector3d(this.max.x,this.max.y,this.min.z),
                new Vector3d(this.max),
                new Vector3d(this.max.x,this.min.y,this.max.z),
                new Vector3d(this.max.x,this.min.y,this.min.z)
        );
    }
    
    @Override public double getWidth() {
        return Math.abs(this.max.x-this.min.x);
    }

    private boolean isFiniteAndNotMaxed(double d) {
        return Double.isFinite(d) && Math.abs(d)<MAX_VALUE;
    }

    private boolean isFiniteAndNotMaxed(double x, double y, double z) {
        return isFiniteAndNotMaxed(x) && isFiniteAndNotMaxed(y) && isFiniteAndNotMaxed(z);
    }

    public boolean isInside(BlockPosAPI<?> pos) {
        return isInside(pos.x(),pos.y(),pos.z());
    }

    @Override public boolean isInside(Vector3d pos) {
        return isInside(pos.x,pos.y,pos.z);
    }

    public boolean isInside(Vector3i pos) {
        return isInside(pos.x,pos.y,pos.z);
    }

    public boolean isInside(double x, double y, double z) {
        return x>=this.min.x && x<=this.max.x && y>=this.min.y && y<=this.max.y && z>=this.min.z && z<=this.max.z;
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
        if(x==1d && y==1d && z==1d) return this;
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
        return getWidth()/2d;
    }

    public double radiusY() {
        return getHeight()/2d;
    }

    public double radiusZ() {
        return getDepth()/2d;
    }
    
    @Override public Vector2d random2D() {
        return VectorHelper.random2D(new Vector2d(this.min.x,this.min.y),new Vector2d(this.max.x,this.max.y));
    }
    
    @Override public Vector3d random3D() {
        return VectorHelper.random3D(this.min,this.max);
    }

    public Box shrink(double d) {
        return expand(-d,-d,-d);
    }

    public Box shrink(double x, double y, double z) {
        return expand(-x,-y,-z);
    }
    
    @Override public Shape2D[] getAs2DArray() {
        return new Shape2D[]{
                new Plane(new Vector3d(this.min),new Vector3d(this.max.x,this.max.y,this.min.z)),
                new Plane(new Vector3d(this.min),new Vector3d(this.max.x,this.min.y,this.max.z)),
                new Plane(new Vector3d(this.min),new Vector3d(this.min.x,this.max.y,this.max.z)),
                new Plane(new Vector3d(this.min.x,this.max.y,this.min.z),new Vector3d(this.max)),
                new Plane(new Vector3d(this.max.x,this.min.y,this.min.z),new Vector3d(this.max)),
                new Plane(new Vector3d(this.min.x,this.min.y,this.max.z),new Vector3d(this.max))
        };
    }
}