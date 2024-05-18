package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import org.joml.Vector2d;
import org.joml.Vector3d;

@Getter
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
    
    public Vector3d getWorldCoordinate(Vector2d relative) {
        Vector3d angles = getDirectionAngles();
        double distance = relative.distance(ZERO_2D)*this.direction.distance(ZERO_3D);
        return new Vector3d(relative,0d).rotateX(angles.x).rotateY(angles.y).rotateZ(angles.z).mul(distance);
    }
    
    @Override public boolean isInside(Vector3d pos) {
        return isInsideRelative(getRelativeCoordinate(pos));
    }
    
    public abstract boolean isInsideRelative(Vector2d pos);
}