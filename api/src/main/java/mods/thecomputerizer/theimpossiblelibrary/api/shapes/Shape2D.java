package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.function.Supplier;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

@SuppressWarnings("unused") @Getter
public abstract class Shape2D implements Shape {
    
    private static final Vector2d ZERO_2D = VectorHelper.zero2D();
    private static final Vector3d ZERO_3D = VectorHelper.zero3D();
    
    protected final Vector3d direction;
    
    protected Shape2D(Vector3d direction) {
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
    
    public Vector3d getDirectionAngles() {
        double angleX = new Vector2d(0d,1d).angle(new Vector2d(this.direction.z,this.direction.y));
        double angleY = new Vector2d(0d,1d).angle(new Vector2d(this.direction.x,this.direction.z));
        double angleZ = new Vector2d(0d,1d).angle(new Vector2d(this.direction.x,this.direction.y));
        return new Vector3d(angleX,angleY,angleZ);
    }
    
    public VectorSupplier2D getOutlineSupplier(Box bounds) {
        return getVectorSupplier(bounds);
    }
    
    public Vector2d getRelativeCoordinate(double x, double y, double z) {
        return getRelativeCoordinate(new Vector3d(x,y,z));
    }
    
    public Vector2d getRelativeCoordinate(Vector3d world) {
        int inf = 0;
        for(double d : VectorHelper.toArray(world)) {
            if(d==POSITIVE_INFINITY && inf==0) inf = 1;
            else if(d==NEGATIVE_INFINITY) inf = inf==1 ? 2 : 1;
        }
        if(inf>0) return inf==1 ? VectorHelper.inf2D() : VectorHelper.negInf2D();
        Vector3d angles = getDirectionAngles();
        double distance = world.distance(ZERO_3D)/this.direction.distance(ZERO_3D);
        Vector3d rotated = world.rotateX(-angles.x,new Vector3d()).rotateY(-angles.y).rotateZ(-angles.z);
        return new Vector2d(rotated.x,rotated.y).mul(distance);
    }
    
    @Override public Shape2D getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    @Override public Shape2D getScaled(Vector2d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Shape2D getScaled(double scaleX, double scaleY) {
        return getScaled(scaleX,scaleY,scaleX);
    }
    
    @Override public Shape2D getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public abstract Shape2D getScaled(double scaleX, double scaleY, double scaleZ);
    
    public abstract VectorSupplier2D getVectorSupplier(Box bounds);
    
    public Vector3d getWorldCoordinate(double x, double y) {
        return getWorldCoordinate(new Vector2d(x,y));
    }
    
    public Vector3d getWorldCoordinate(Vector2d relative) {
        if(Misc.equalsAny(POSITIVE_INFINITY,relative.x,relative.y) ||
           Misc.equalsAny(NEGATIVE_INFINITY,relative.x,relative.y))
            return Misc.equalsAny(NEGATIVE_INFINITY,relative.x,relative.y) ?
                    VectorHelper.negInf3D() : VectorHelper.inf3D();
        Vector3d angles = getDirectionAngles();
        double distance = relative.distance(ZERO_2D)*this.direction.distance(ZERO_3D);
        return new Vector3d(relative,0d).rotateX(angles.x).rotateY(angles.y).rotateZ(angles.z).mul(distance);
    }
    
    @Override public boolean isInside(double x, double y, double z) {
        return isInside(new Vector3d(x,y,z));
    }
    
    @Override public boolean isInside(Vector3d pos) {
        return isInsideRelative(new Vector2d(pos.x,pos.y));
    }
    
    public abstract boolean isInsideRelative(Vector2d pos);
    
    @Override public FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, Supplier<ColorCache> colorGenerator) {
        return new FuzzBall(
                () -> ScreenHelper.randomPointSupplier2D(this::random2D,minCount,maxCount),
                () -> ScreenHelper.randomPointSupplier3D(this::random3D,minCount,maxCount),
                () -> RandomHelper.randomFloat(minWidth,maxWidth),
                colorGenerator
        );
    }
    
    public boolean sameDirection(Shape2D other) {
        return Misc.equalsNullable(this.direction,other.direction);
    }
}