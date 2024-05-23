package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract class Shape3D implements Shape {
    
    @Override public Shape3D copy() {
        return getScaled(1d,1d,1d);
    }
    
    public VectorSupplier3D getOutlineSupplier(Box bounds) {
        return getVectorSupplier(bounds);
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
    
    public abstract VectorSupplier3D getVectorSupplier(Box bounds);
    
    @Override public FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, Supplier<ColorCache> colorGenerator) {
        return new FuzzBall(
                () -> ScreenHelper.randomPointSupplier2D(this::random2D,minCount,maxCount),
                () -> ScreenHelper.randomPointSupplier3D(this::random3D,minCount,maxCount),
                () -> RandomHelper.randomFloat(minWidth,maxWidth),
                colorGenerator
        );
    }
}