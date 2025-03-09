package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

import java.util.function.Supplier;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

@SuppressWarnings("unused") @Getter
public abstract class Shape2D implements Shape {
    
    private static final Vector2 ZERO_2D = VectorHelper.zero2D();
    private static final Vector3 ZERO_3D = VectorHelper.zero3D();
    
    protected final Vector3 direction;
    
    protected Shape2D(Vector3 direction) {
        this.direction = direction;
    }
    
    @Override public Shape2D copy() {
        return getScaled(1d,1d);
    }
    
    @Override public Shape2D[] getAs2DArray() {
        return new Shape2D[]{this};
    }
    
    @Override public double getBoundedZ(double x, double y,double z) {
        return z;
    }
    
    public Vector3 getDirectionAngles() {
        double angleX = new Vector2(0d,1d).angle(new Vector2(this.direction.dZ(),this.direction.dY()));
        double angleY = new Vector2(0d,1d).angle(new Vector2(this.direction.dX(),this.direction.dZ()));
        double angleZ = new Vector2(0d,1d).angle(new Vector2(this.direction.dX(),this.direction.dY()));
        return new Vector3(angleX,angleY,angleZ);
    }
    
    public VectorSupplier2D getOutlineSupplier(Box bounds) {
        return getVectorSupplier(bounds);
    }
    
    public Vector2 getRelativeCoordinate(double x, double y, double z) {
        return getRelativeCoordinate(new Vector3(x,y,z));
    }
    
    public Vector2 getRelativeCoordinate(Vector3 world) {
        int inf = 0;
        for(double d : world.dArrray()) {
            if(d==POSITIVE_INFINITY && inf==0) inf = 1;
            else if(d==NEGATIVE_INFINITY) inf = inf==1 ? 2 : 1;
        }
        if(inf>0) return inf==1 ? VectorHelper.inf2D() : VectorHelper.negInf2D();
        Vector3 angles = getDirectionAngles();
        double distance = world.distance(ZERO_3D)/this.direction.distance(ZERO_3D);
        Vector3 rotated = world.rotateX(-angles.dX(),new Vector3()).rotateY(-angles.dY()).rotateZ(-angles.dZ());
        return new Vector2(rotated.dX(),rotated.dY()).mulScalar(distance);
    }
    
    @Override public Shape2D getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    @Override public Shape2D getScaled(Vector2 scale) {
        return getScaled(scale.dX(),scale.dY());
    }
    
    @Override public Shape2D getScaled(double scaleX, double scaleY) {
        return getScaled(scaleX,scaleY,scaleX);
    }
    
    @Override public Shape2D getScaled(Vector3 scale) {
        return getScaled(scale.dX(),scale.dY());
    }
    
    @Override public abstract Shape2D getScaled(double scaleX, double scaleY, double scaleZ);
    
    public abstract VectorSupplier2D getVectorSupplier(Box bounds);
    
    public Vector3 getWorldCoordinate(double x, double y) {
        return getWorldCoordinate(new Vector2(x,y));
    }
    
    public Vector3 getWorldCoordinate(Vector2 relative) {
        if(Misc.equalsAny(POSITIVE_INFINITY,relative.dX(),relative.dY()) ||
           Misc.equalsAny(NEGATIVE_INFINITY,relative.dX(),relative.dY()))
            return Misc.equalsAny(NEGATIVE_INFINITY,relative.dX(),relative.dY()) ?
                    VectorHelper.negInf3D() : VectorHelper.inf3D();
        Vector3 angles = getDirectionAngles();
        double distance = relative.distance(ZERO_2D)*this.direction.distance(ZERO_3D);
        return new Vector3(relative,0d).rotateX(angles.dX()).rotateY(angles.dY()).rotateZ(angles.dZ()).mulScalar(distance);
    }
    
    @Override public boolean isInside(double x, double y, double z) {
        return isInside(new Vector3(x,y,z));
    }
    
    @Override public boolean isInside(Vector3 pos) {
        return isInsideRelative(new Vector2(pos.dX(),pos.dY()));
    }
    
    public abstract boolean isInsideRelative(Vector2 pos);
    
    @Override public FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, Supplier<ColorCache> colorGenerator) {
        return new FuzzBall(
                () -> ScreenHelper.randomPointSupplier2D(this::random2D,minCount,maxCount),
                null,
                () -> RandomHelper.randomFloat(minWidth,maxWidth),
                colorGenerator
        );
    }
    
    public boolean sameDirection(Shape2D other) {
        return Misc.equalsNullable(this.direction,other.direction);
    }
}