package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
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
        @Override public boolean isInside(BlockPosAPI<?> pos, double tolerance) { return true; }
        @Override public boolean isInside(Vector3d pos) { return true; }
        @Override public boolean isInside(Vector3d pos, double tolerance) { return true; }
        @Override public boolean isInside(Vector3i pos) { return true; }
        @Override public boolean isInside(Vector3i pos, double tolerance) { return true; }
        @Override public boolean isInside(double x, double y, double z) { return true; }
        @Override public boolean isInside(double x, double y, double z, double tolerance) { return true; }
        @Override public boolean isInsideX(double x) { return true; }
        @Override public boolean isInsideX(double x, double tolerance) { return true; }
        @Override public boolean isInsideY(double y) { return true; }
        @Override public boolean isInsideY(double y, double tolerance) { return true; }
        @Override public boolean isInsideZ(double z) { return true; }
        @Override public boolean isInsideZ(double z, double tolerance) { return true; }
    };
    public static final Box ZERO = new Box(0d,0d,0d,0d,0d,0d) {
        @Override public boolean isInside(BlockPosAPI<?> pos) { return false; }
        @Override public boolean isInside(BlockPosAPI<?> pos, double tolerance) { return true; }
        @Override public boolean isInside(Vector3d pos) { return false; }
        @Override public boolean isInside(Vector3d pos, double tolerance) { return true; }
        @Override public boolean isInside(Vector3i pos) { return false; }
        @Override public boolean isInside(Vector3i pos, double tolerance) { return true; }
        @Override public boolean isInside(double x, double y, double z) { return false; }
        @Override public boolean isInside(double x, double y, double z, double tolerance) { return true; }
        @Override public boolean isInsideX(double x) { return true; }
        @Override public boolean isInsideX(double x, double tolerance) { return true; }
        @Override public boolean isInsideY(double y) { return true; }
        @Override public boolean isInsideY(double y, double tolerance) { return true; }
        @Override public boolean isInsideZ(double z) { return true; }
        @Override public boolean isInsideZ(double z, double tolerance) { return true; }
    };

    public final Vector3d min;
    public final Vector3d max;
    public final Vector3d center;
    
    /**
     See ShapeHelper for alternative construction methods
     */
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
        return ShapeHelper.box(min,max);
    }
    
    @Override public boolean checkToleranceBounds(Vector3d center, Box bounds) {
        return bounds.expand(getWidth()/2d,getHeight()/2d,getDepth()/2d).isInside(getCenter(center));
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
    
    @Override public boolean equals(Object other) {
        if(this==other) return true;
        if(Objects.isNull(other)) return false;
        if(other.getClass()==Box.class) {
            Box box = (Box)other;
            return Misc.equalsNullable(this.min,box.max) && Misc.equalsNullable(this.max,box.max);
        }
        return false;
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
        return ShapeHelper.box(this.min.sub(x,y,z,new Vector3d()),this.max.add(x,y,z,new Vector3d()));
    }
    
    @Override public double getBoundedX(double x, double y, double z) {
        return Math.max(this.min.x,Math.min(this.max.x,x));
    }
    
    @Override public double getBoundedY(double x, double y, double z) {
        return Math.max(this.min.y,Math.min(this.max.y,y));
    }
    
    @Override public double getBoundedZ(double x, double y, double z) {
        return Math.max(this.min.z,Math.min(this.max.z,z));
    }
    
    @Override public Vector3d getCenter(Vector3d center) {
        return center.add(this.center,new Vector3d());
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
    
    @Override public VectorSupplier3D getVectorSupplier(Box bounds) {
        Box bounded = intersection(bounds);
        return VectorStreams.get3D(
                new Vector3d(bounded.min),
                new Vector3d(bounded.min.x,bounded.min.y,bounded.max.z),
                new Vector3d(bounded.min.x,bounded.max.y,bounded.max.z),
                new Vector3d(bounded.min.x,bounded.max.y,bounded.min.z),
                new Vector3d(bounded.max.x,bounded.max.y,bounded.min.z),
                new Vector3d(bounded.max),
                new Vector3d(bounded.max.x,bounded.min.y,bounded.max.z),
                new Vector3d(bounded.max.x,bounded.min.y,bounded.min.z)
        );
    }
    
    @Override public double getWidth() {
        return Math.abs(this.max.x-this.min.x);
    }
    
    public Box intersection(Box other) {
        return new Box(Math.max(this.min.x,other.min.x),Math.max(this.min.y,other.min.y),
                       Math.max(this.min.z,other.min.z),Math.min(this.max.x,other.max.x),
                       Math.min(this.max.y,other.max.y), Math.min(this.max.z,other.max.z));
    }

    private boolean isFiniteAndNotMaxed(double d) {
        return Double.isFinite(d) && Math.abs(d)<MAX_VALUE;
    }

    private boolean isFiniteAndNotMaxed(double x, double y, double z) {
        return isFiniteAndNotMaxed(x) && isFiniteAndNotMaxed(y) && isFiniteAndNotMaxed(z);
    }
    
    public boolean isInside(BlockPosAPI<?> pos) {
        return isInsideX(pos.x(),0d) && isInsideY(pos.y(),0d) && isInsideZ(pos.z(),0d);
    }

    public boolean isInside(BlockPosAPI<?> pos, double tolerance) {
        return isInsideX(pos.x(),tolerance) && isInsideY(pos.y(),tolerance) && isInsideZ(pos.z(),tolerance);
    }
    
    @Override public boolean isInside(Vector3d pos) {
        return isInsideX(pos.x,0d) && isInsideY(pos.y,0d) && isInsideZ(pos.z,0d);
    }

    public boolean isInside(Vector3d pos, double tolerance) {
        return isInsideX(pos.x,tolerance) && isInsideY(pos.y,tolerance) && isInsideZ(pos.z,tolerance);
    }
    
    public boolean isInside(Vector3i pos) {
        return isInsideX(pos.x,0d) && isInsideY(pos.y,0d) && isInsideZ(pos.z,0d);
    }

    public boolean isInside(Vector3i pos, double tolerance) {
        return isInsideX(pos.x,tolerance) && isInsideY(pos.y,tolerance) && isInsideZ(pos.z,tolerance);
    }
    
    public boolean isInside(double x, double y, double z) {
        return isInsideX(x,0d) && isInsideY(y,0d) && isInsideZ(z,0d);
    }

    public boolean isInside(double x, double y, double z, double tolerance) {
        return isInsideX(x,tolerance) && isInsideY(y,tolerance) && isInsideZ(z,tolerance);
    }
    
    public boolean isInsideX(double x) {
        return isInsideX(x,0d);
    }
    
    public boolean isInsideX(double x, double tolerance) {
        return x>this.min.x-tolerance && x<this.max.x+tolerance;
    }
    
    public boolean isInsideY(double y) {
        return isInsideY(y,0d);
    }
    
    public boolean isInsideY(double y, double tolerance) {
        return y>this.min.y-tolerance && y<this.max.y+tolerance;
    }
    
    public boolean isInsideZ(double z) {
        return isInsideZ(z,0d);
    }
    
    public boolean isInsideZ(double z, double tolerance) {
        return z>this.min.z-tolerance && z<this.max.z+tolerance;
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
                ShapeHelper.box(this.min.add(x,y,z,new Vector3d()),this.max.add(x,y,z,new Vector3d()));
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
        return VectorHelper.randomD(new Vector2d(this.min.x, this.min.y), new Vector2d(this.max.x, this.max.y));
    }
    
    @Override public Vector3d random3D() {
        return VectorHelper.randomD(this.min, this.max);
    }

    public Box shrink(double d) {
        return expand(-d,-d,-d);
    }

    public Box shrink(double x, double y, double z) {
        return expand(-x,-y,-z);
    }
    
    @Override public Shape2D[] getAs2DArray() {
        return new Shape2D[]{
                ShapeHelper.plane(new Vector3d(this.min),new Vector3d(this.max.x,this.max.y,this.min.z)),
                ShapeHelper.plane(new Vector3d(this.min),new Vector3d(this.max.x,this.min.y,this.max.z)),
                ShapeHelper.plane(new Vector3d(this.min),new Vector3d(this.min.x,this.max.y,this.max.z)),
                ShapeHelper.plane(new Vector3d(this.min.x,this.max.y,this.min.z),new Vector3d(this.max)),
                ShapeHelper.plane(new Vector3d(this.max.x,this.min.y,this.min.z),new Vector3d(this.max)),
                ShapeHelper.plane(new Vector3d(this.min.x,this.min.y,this.max.z),new Vector3d(this.max))
        };
    }
}