package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import org.joml.Vector2d;
import org.joml.Vector3d;

public abstract class Shape3D implements Shape {
    
    @Override public Shape3D copy() {
        return getScaled(1d,1d,1d);
    }
    
    @Override public Shape3D getScaled(double scale) {
        return getScaled(scale,scale,scale);
    }
    
    @Override public Shape3D getScaled(Vector2d scale) {
        return getScaled(scale.x,scale.y,scale.x);
    }
    
    @Override public Shape3D getScaled(double scaleH, double scaleV) {
        return getScaled(scaleH,scaleV,scaleH);
    }
    
    @Override public Shape3D getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y,scale.z);
    }
    
    @Override public abstract Shape3D getScaled(double scaleX, double scaleY, double scaleZ);
}