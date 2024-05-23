package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface Shape {
    
    boolean checkToleranceBounds(Vector3d center, Box bounds);
    
    default Shape copy() {
        return getScaled(1d,1d,1d);
    }
    
    Shape2D[] getAs2DArray();
    
    default Vector3d getBounded(Vector3d pos) {
        return new Vector3d(getBoundedX(pos),getBoundedY(pos),getBoundedZ(pos));
    }
    
    default Vector3d getBounded(double x, double y, double z) {
        return new Vector3d(getBoundedX(x,y,z),getBoundedY(x,y,z),getBoundedZ(x,y,z));
    }
    
    default double getBoundedX(Vector3d pos) {
        return getBoundedX(pos.x,pos.y,pos.z);
    }
    
    double getBoundedX(double x, double y, double z);
    
    default Vector2d getBoundedXY(Vector2d xy) {
        return new Vector2d(getBoundedX(xy.x,xy.y,0d),getBoundedY(xy.x,xy.y,0d));
    }
    
    default Vector2d getBoundedXY(double x, double y) {
        return new Vector2d(getBoundedX(x,y,0d),getBoundedY(x,y,0d));
    }
    
    default Vector2d getBoundedXZ(Vector2d xz) {
        return new Vector2d(getBoundedX(xz.x,0d,xz.y),getBoundedZ(xz.x,0d,xz.y));
    }
    
    default Vector2d getBoundedXZ(double x, double z) {
        return new Vector2d(getBoundedX(x,0d,z),getBoundedZ(x,0d,z));
    }
    
    default double getBoundedY(Vector3d pos) {
        return getBoundedY(pos.x,pos.y,pos.z);
    }
    
    double getBoundedY(double x, double y, double z);
    
    default Vector2d getBoundedYZ(Vector2d yz) {
        //noinspection SuspiciousNameCombination
        return new Vector2d(getBoundedY(0d,yz.x,yz.y),getBoundedZ(0d,yz.x,yz.y));
    }
    
    default Vector2d getBoundedYZ(double y, double z) {
        return new Vector2d(getBoundedY(0d,y,z),getBoundedZ(0d,y,z));
    }
    
    default double getBoundedZ(Vector3d pos) {
        return getBoundedZ(pos.x,pos.y,pos.z);
    }
    
    double getBoundedZ(double x, double y, double z);
    
    default Vector3d getCenter(Vector3d center) {
        return center;
    }
    
    double getDepth();
    double getHeight();
    
    default Shape getScaled(double scale) {
        return getScaled(scale,scale,scale);
    }
    
    default Shape getScaled(Vector2d scale) {
        return getScaled(scale.x,scale.y,scale.x);
    }
    
    default Shape getScaled(double scaleH, double scaleV) {
        return getScaled(scaleH,scaleV,scaleH);
    }
    
    default Shape getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y,scale.z);
    }
    
    Shape getScaled(double scaleX, double scaleY, double scaleZ);
    double getWidth();
    boolean isInside(double x, double y, double z);
    
    default boolean isInside(Vector3d pos) {
        return isInside(pos.x,pos.y,pos.z);
    }
    
    default FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth) {
        return makeFuzzBall(minCount,maxCount,minWidth,maxWidth,
                            () -> new ColorCache(0f,0f,0f,RandomHelper.randomFloat(0.75f,1f)));
    }
    
    FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, Supplier<ColorCache> colorGenerator);
    Vector2d random2D();
    Vector3d random3D();
}