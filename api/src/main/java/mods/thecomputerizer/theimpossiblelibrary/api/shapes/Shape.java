package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface Shape {
    
    default Shape copy() {
        return getScaled(1d,1d,1d);
    }
    
    Shape2D[] getAs2DArray();
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