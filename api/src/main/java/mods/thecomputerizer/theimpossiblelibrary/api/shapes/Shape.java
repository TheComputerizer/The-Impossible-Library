package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.FuzzBall;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.BLACK;

@SuppressWarnings("unused")
public interface Shape {
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean checkToleranceBounds(Vector3 center, Box bounds);
    
    default Shape copy() {
        return getScaled(1d,1d,1d);
    }
    
    Shape2D[] getAs2DArray();
    
    default Vector3 getBounded(Vector3 pos) {
        return new Vector3(getBoundedX(pos),getBoundedY(pos),getBoundedZ(pos));
    }
    
    default Vector3 getBounded(double x, double y, double z) {
        return new Vector3(getBoundedX(x,y,z),getBoundedY(x,y,z),getBoundedZ(x,y,z));
    }
    
    default double getBoundedX(Vector3 pos) {
        return getBoundedX(pos.dX(),pos.dY(),pos.dZ());
    }
    
    double getBoundedX(double x, double y, double z);
    
    default Vector2 getBoundedXY(Vector2 xy) {
        return new Vector2(getBoundedX(xy.dX(),xy.dY(),0d),getBoundedY(xy.dX(),xy.dY(),0d));
    }
    
    default Vector2 getBoundedXY(double x, double y) {
        return new Vector2(getBoundedX(x,y,0d),getBoundedY(x,y,0d));
    }
    
    default Vector2 getBoundedXZ(Vector2 xz) {
        return new Vector2(getBoundedX(xz.dX(),0d,xz.dY()),getBoundedZ(xz.dX(),0d,xz.dY()));
    }
    
    default Vector2 getBoundedXZ(double x, double z) {
        return new Vector2(getBoundedX(x,0d,z),getBoundedZ(x,0d,z));
    }
    
    default double getBoundedY(Vector3 pos) {
        return getBoundedY(pos.dX(),pos.dY(),pos.dZ());
    }
    
    double getBoundedY(double x, double y, double z);
    
    default Vector2 getBoundedYZ(Vector2 yz) {
        return new Vector2(getBoundedY(0d,yz.dX(),yz.dY()),getBoundedZ(0d,yz.dX(),yz.dY()));
    }
    
    default Vector2 getBoundedYZ(double y, double z) {
        return new Vector2(getBoundedY(0d,y,z),getBoundedZ(0d,y,z));
    }
    
    default double getBoundedZ(Vector3 pos) {
        return getBoundedZ(pos.dX(),pos.dY(),pos.dZ());
    }
    
    double getBoundedZ(double x, double y, double z);
    
    default Vector3 getCenter(Vector3 center) {
        return center;
    }
    
    double getDepth();
    double getHeight();
    
    default Shape getScaled(double scale) {
        return getScaled(scale,scale,scale);
    }
    
    default Shape getScaled(Vector2 scale) {
        return getScaled(scale.dX(),scale.dY(),scale.dX());
    }
    
    default Shape getScaled(double scaleH, double scaleV) {
        return getScaled(scaleH,scaleV,scaleH);
    }
    
    default Shape getScaled(Vector3 scale) {
        return getScaled(scale.dX(),scale.dY(),scale.dZ());
    }
    
    Shape getScaled(double scaleX, double scaleY, double scaleZ);
    double getWidth();
    boolean isInside(double x, double y, double z);
    
    default boolean isInside(Vector3 pos) {
        return isInside(pos.dX(),pos.dY(),pos.dZ());
    }
    
    default FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth) {
        return makeFuzzBall(minCount,maxCount,minWidth,maxWidth,BLACK,0.75f,1f);
    }
    
    default FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, ColorCache baseColor,
            float minAlpha, float maxAlpha) {
        return makeFuzzBall(minCount,maxCount,minWidth,maxWidth,baseColor,RandomHelper.randomFloat(0.75f,1f));
    }
    
    default FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, ColorCache baseColor,
            float alpha) {
        return makeFuzzBall(minCount,maxCount,minWidth,maxWidth,() -> baseColor.withAlpha(alpha));
    }
    
    FuzzBall makeFuzzBall(int minCount, int maxCount, float minWidth, float maxWidth, Supplier<ColorCache> colorGenerator);
    Vector2 random2D();
    Vector3 random3D();
}