package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;

import java.util.Objects;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

@SuppressWarnings("unused")
public class Box extends Shape3D { //TODO Finish edge cases for weird doubles

    public static final Box INFINITE = new Box(NEGATIVE_INFINITY,NEGATIVE_INFINITY,NEGATIVE_INFINITY,
                                               POSITIVE_INFINITY,POSITIVE_INFINITY,POSITIVE_INFINITY) {
        @Override public Box div(double x, double y, double z) { return this; }
        @Override public Box expand(double x, double y, double z) { return this; }
        @Override public double getBoundedX(double x, double y, double z) { return x; }
        @Override public double getBoundedY(double x, double y, double z) { return y; }
        @Override public double getBoundedZ(double x, double y, double z) { return z; }
        @Override public double getDepth() { return POSITIVE_INFINITY; }
        @Override public double getHeight() { return POSITIVE_INFINITY; }
        @Override public double getWidth() { return POSITIVE_INFINITY; }
        @Override public boolean isInside(BlockPosAPI<?> pos) { return true; }
        @Override public boolean isInside(BlockPosAPI<?> pos, double tolerance) { return true; }
        @Override public boolean isInside(Vector3 pos) { return true; }
        @Override public boolean isInside(Vector3 pos, double tolerance) { return true; }
        @Override public boolean isInside(double x, double y, double z) { return true; }
        @Override public boolean isInside(double x, double y, double z, double tolerance) { return true; }
        @Override public boolean isInsideX(double x) { return true; }
        @Override public boolean isInsideX(double x, double tolerance) { return true; }
        @Override public boolean isInsideY(double y) { return true; }
        @Override public boolean isInsideY(double y, double tolerance) { return true; }
        @Override public boolean isInsideZ(double z) { return true; }
        @Override public boolean isInsideZ(double z, double tolerance) { return true; }
        @Override public Box offset(double x, double y, double z) { return this; }
        @Override public Box mul(double x, double y, double z) { return this; }
        @Override public Box shrink(double x, double y, double z) { return this; }
    };
    public static final Box ZERO = new Box(0d,0d,0d,0d,0d,0d) {
        @Override public boolean isInside(BlockPosAPI<?> pos) { return false; }
        @Override public boolean isInside(BlockPosAPI<?> pos, double tolerance) { return true; }
        @Override public boolean isInside(Vector3 pos) { return false; }
        @Override public boolean isInside(Vector3 pos, double tolerance) { return true; }
        @Override public boolean isInside(double x, double y, double z) { return false; }
        @Override public boolean isInside(double x, double y, double z, double tolerance) { return true; }
        @Override public boolean isInsideX(double x) { return true; }
        @Override public boolean isInsideX(double x, double tolerance) { return true; }
        @Override public boolean isInsideY(double y) { return true; }
        @Override public boolean isInsideY(double y, double tolerance) { return true; }
        @Override public boolean isInsideZ(double z) { return true; }
        @Override public boolean isInsideZ(double z, double tolerance) { return true; }
    };

    public final Vector3 min;
    public final Vector3 max;
    public final Vector3 center;
    
    /**
     See ShapeHelper for alternative construction methods
     */
    public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.min = new Vector3(Math.min(x1,x2),Math.min(y1,y2),Math.min(z1,z2));
        this.max = new Vector3(Math.max(x1,x2),Math.max(y1,y2),Math.max(z1,z2));
        this.center = VectorHelper.getCenter(this.min, this.max);
    }

    /**
     * Returns a new box with the smallest min values and the biggest max values from the input boxes.
     */
    public Box add(Box ... boxes) {
        if(Objects.isNull(boxes) || boxes.length==0) return this;
        Vector3 min = this.min.min(boxes[0].min,new Vector3());
        Vector3 max = this.max.max(boxes[0].max,new Vector3());
        for(int i=1;i<boxes.length;i++) {
            min = min.min(boxes[i].min);
            max = max.max(boxes[i].max);
        }
        return ShapeHelper.box(min,max);
    }
    
    @Override public boolean checkToleranceBounds(Vector3 center, Box bounds) {
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
        return ShapeHelper.box(this.min.sub(x,y,z,new Vector3()),this.max.add(x,y,z,new Vector3()));
    }
    
    @Override public double getBoundedX(double x, double y, double z) {
        return Math.max(this.min.dX(),Math.min(this.max.dX(),x));
    }
    
    @Override public double getBoundedY(double x, double y, double z) {
        return Math.max(this.min.dY(),Math.min(this.max.dY(),y));
    }
    
    @Override public double getBoundedZ(double x, double y, double z) {
        return Math.max(this.min.dZ(),Math.min(this.max.dZ(),z));
    }
    
    @Override public Vector3 getCenter(Vector3 center) {
        return center.add(this.center,new Vector3());
    }
    
    @Override public double getDepth() {
        return Math.abs(this.max.dZ()-this.min.dZ());
    }
    
    @Override public double getHeight() {
        return Math.abs(this.max.dY()-this.min.dY());
    }
    
    @Override public Box getScaled(double scale) {
        return mul(scale,scale,scale);
    }
    
    @Override public Box getScaled(Vector2 scale) {
        return mul(scale.dX(),scale.dY(),scale.dX());
    }
    
    @Override public Box getScaled(double scaleH, double scaleV) {
        return mul(scaleH,scaleV,scaleH);
    }
    
    @Override public Box getScaled(Vector3 scale) {
        return mul(scale.dX(),scale.dY(),scale.dZ());
    }
    
    @Override public Box getScaled(double scaleX, double scaleY, double scaleZ) {
        return mul(scaleX,scaleY,scaleZ);
    }
    
    @Override public VectorSupplier3D getVectorSupplier(Box bounds) {
        Box bounded = intersection(bounds);
        return VectorStreams.get3D(
                new Vector3(bounded.min),
                new Vector3(bounded.min.dX(),bounded.min.dY(),bounded.max.dZ()),
                new Vector3(bounded.min.dX(),bounded.max.dY(),bounded.max.dZ()),
                new Vector3(bounded.min.dX(),bounded.max.dY(),bounded.min.dZ()),
                new Vector3(bounded.max.dX(),bounded.max.dY(),bounded.min.dZ()),
                new Vector3(bounded.max),
                new Vector3(bounded.max.dX(),bounded.min.dY(),bounded.max.dZ()),
                new Vector3(bounded.max.dX(),bounded.min.dY(),bounded.min.dZ())
        );
    }
    
    @Override public double getWidth() {
        return Math.abs(this.max.dX()-this.min.dX());
    }
    
    public Box intersection(Box other) {
        return new Box(Math.max(this.min.dX(),other.min.dX()),Math.max(this.min.dY(),other.min.dY()),
                       Math.max(this.min.dZ(),other.min.dZ()),Math.min(this.max.dX(),other.max.dX()),
                       Math.min(this.max.dY(),other.max.dY()), Math.min(this.max.dZ(),other.max.dZ()));
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
    
    @Override public boolean isInside(Vector3 pos) {
        return isInsideX(pos.dX(),0d) && isInsideY(pos.dY(),0d) && isInsideZ(pos.dZ(),0d);
    }

    public boolean isInside(Vector3 pos, double tolerance) {
        return isInsideX(pos.dX(),tolerance) && isInsideY(pos.dY(),tolerance) && isInsideZ(pos.dZ(),tolerance);
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
        return x>this.min.dX()-tolerance && x<this.max.dX()+tolerance;
    }
    
    public boolean isInsideXY(double x, double y) {
        return isInsideX(x,0d) && isInsideY(y,0d);
    }
    
    public boolean isInsideXY(double x, double y, double tolerance) {
        return isInsideX(x,tolerance) && isInsideY(y,tolerance);
    }
    
    public boolean isInsideY(double y) {
        return isInsideY(y,0d);
    }
    
    public boolean isInsideY(double y, double tolerance) {
        return y>this.min.dY()-tolerance && y<this.max.dY()+tolerance;
    }
    
    public boolean isInsideZ(double z) {
        return isInsideZ(z,0d);
    }
    
    public boolean isInsideZ(double z, double tolerance) {
        return z>this.min.dZ()-tolerance && z<this.max.dZ()+tolerance;
    }

    public double maxX() {
        return this.max.dX();
    }
    
    public double maxY() {
        return this.max.dY();
    }
    
    public double maxZ() {
        return this.max.dZ();
    }
    
    public double minX() {
        return this.min.dX();
    }
    
    public double minY() {
        return this.min.dY();
    }
    
    public double minZ() {
        return this.min.dZ();
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
                ShapeHelper.box(this.min.add(x,y,z,new Vector3()),this.max.add(x,y,z,new Vector3()));
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
    
    @Override public Vector2 random2D() {
        return VectorHelper.randomD(new Vector2(this.min.dX(), this.min.dY()), new Vector2(this.max.dX(), this.max.dY()));
    }
    
    @Override public Vector3 random3D() {
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
                ShapeHelper.plane(new Vector3(this.min),new Vector3(this.max.dX(),this.max.dY(),this.min.dZ())),
                ShapeHelper.plane(new Vector3(this.min),new Vector3(this.max.dX(),this.min.dY(),this.max.dZ())),
                ShapeHelper.plane(new Vector3(this.min),new Vector3(this.min.dX(),this.max.dY(),this.max.dZ())),
                ShapeHelper.plane(new Vector3(this.min.dX(),this.max.dY(),this.min.dZ()),new Vector3(this.max)),
                ShapeHelper.plane(new Vector3(this.max.dX(),this.min.dY(),this.min.dZ()),new Vector3(this.max)),
                ShapeHelper.plane(new Vector3(this.min.dX(),this.min.dY(),this.max.dZ()),new Vector3(this.max))
        };
    }
}