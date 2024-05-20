package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

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
    
    public Vector3d getDirectionAngles() {
        double angleX = new Vector2d(0d,1d).angle(new Vector2d(this.direction.z,this.direction.y));
        double angleY = new Vector2d(0d,1d).angle(new Vector2d(this.direction.x,this.direction.z));
        double angleZ = new Vector2d(0d,1d).angle(new Vector2d(this.direction.x,this.direction.y));
        return new Vector3d(angleX,angleY,angleZ);
    }
    
    public VectorSupplier2D getOutlineSupplier() {
        return getVectorSupplier();
    }
    
    public Vector2d getRelativeCoordinate(double x, double y, double z) {
        return getRelativeCoordinate(new Vector3d(x,y,z));
    }
    
    public Vector2d getRelativeCoordinate(Vector3d world) {
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
    public abstract VectorSupplier2D getVectorSupplier();
    
    public Vector3d getWorldCoordinate(double x, double y) {
        return getWorldCoordinate(new Vector2d(x,y));
    }
    
    public Vector3d getWorldCoordinate(Vector2d relative) {
        Vector3d angles = getDirectionAngles();
        double distance = relative.distance(ZERO_2D)*this.direction.distance(ZERO_3D);
        return new Vector3d(relative,0d).rotateX(angles.x).rotateY(angles.y).rotateZ(angles.z).mul(distance);
    }
    
    @Override public boolean isInside(Vector3d pos) {
        return isInsideRelative(getRelativeCoordinate(pos));
    }
    
    public abstract boolean isInsideRelative(Vector2d pos);
    
    @Override public FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth) {
        return new FuzzBall(
                () -> ScreenHelper.randomPointSupplier2D(this::random2D, minCount, maxCount),
                () -> ScreenHelper.randomPointSupplier3D(this::random3D,minCount,maxCount),
                () -> RandomHelper.randomFloat(minWidth, maxWidth),
                () -> new ColorCache(0f, 0f, 0f, RandomHelper.randomFloat(0.75f, 1f))
        );
    }
}