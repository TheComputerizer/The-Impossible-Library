package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import org.joml.Vector2d;
import org.joml.Vector3d;

@SuppressWarnings("unused")
public interface Shape {
    
    default Shape copy() {
        return getScaled(1d,1d,1d);
    }
    
    Shape2D[] getAs2DArray();
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
    boolean isInside(Vector3d pos);
    FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth);
    Vector2d random2D();
    Vector3d random3D();
}