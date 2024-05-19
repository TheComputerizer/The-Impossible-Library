package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;

@SuppressWarnings("unused")
public class VectorHelper {
    
    /**
     These are all private to prevent accidental modification of data.
     The methods below should be called instead to create copies.
     */
    private static final Vector2d INF_2D = new Vector2d(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    private static final Vector2f INF_2F = new Vector2f(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
    private static final Vector3d INF_3D = new Vector3d(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    private static final Vector3f INF_3F = new Vector3f(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
    private static final Vector2d MAX_2D = new Vector2d(Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector2f MAX_2F = new Vector2f(Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector2i MAX_2I = new Vector2i(Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector3d MAX_3D = new Vector3d(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector3f MAX_3F = new Vector3f(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector3i MAX_3I = new Vector3i(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector2d MIN_2D = new Vector2d(Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector2f MIN_2F = new Vector2f(Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector2i MIN_2I = new Vector2i(Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector3d MIN_3D = new Vector3d(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector3f MIN_3F = new Vector3f(Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector3i MIN_3I = new Vector3i(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector2d NEG_INF_2D = new Vector2d(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector2f NEG_INF_2F = new Vector2f(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector3d NEG_INF_3D = new Vector3d(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector3f NEG_INF_3F = new Vector3f(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector2d ZERO_2D = new Vector2d(0d,0d);
    private static final Vector2f ZERO_2F = new Vector2f(0f,0f);
    private static final Vector2i ZERO_2I = new Vector2i(0,0);
    private static final Vector3d ZERO_3D = new Vector3d(0d,0d,0d);
    private static final Vector3f ZERO_3F = new Vector3f(0f,0f,0f);
    private static final Vector3i ZERO_3I = new Vector3i(0,0,0);
    
    public static Vector2d inf2D() {
        return new Vector2d(INF_2D);
    }
    
    public static Vector2f inf2F() {
        return new Vector2f(INF_2F);
    }
    
    public static Vector3d inf3D() {
        return new Vector3d(INF_3D);
    }
    
    public static Vector3f inf3F() {
        return new Vector3f(INF_3F);
    }

    /**
     * Gets a vector in the center of the two inputs.
     * Assumes the min and max have already been properly calculated.
     */
    public static Vector3d getCenter(Vector3d min, Vector3d max) {
        return new Vector3d(min.x+(max.x-min.x)/2d,min.y+(max.y-min.y)/2d,min.z+(max.z-min.z)/2d);
    }
    
    public static Vector2d max2D() {
        return new Vector2d(MAX_2D);
    }
    
    public static Vector2f max2F() {
        return new Vector2f(MAX_2F);
    }
    
    public static Vector2i max2I() {
        return new Vector2i(MAX_2I);
    }
    
    public static Vector3d max3D() {
        return new Vector3d(MAX_3D);
    }
    
    public static Vector3f max3F() {
        return new Vector3f(MAX_3F);
    }
    
    public static Vector3i max3I() {
        return new Vector3i(MAX_3I);
    }
    
    public static Vector2d min2D() {
        return new Vector2d(MIN_2D);
    }
    
    public static Vector2f min2F() {
        return new Vector2f(MIN_2F);
    }
    
    public static Vector2i min2I() {
        return new Vector2i(MIN_2I);
    }
    
    public static Vector3d min3D() {
        return new Vector3d(MIN_3D);
    }
    
    public static Vector3f min3F() {
        return new Vector3f(MIN_3F);
    }
    
    public static Vector3i min3I() {
        return new Vector3i(MIN_3I);
    }
    
    public static Vector2d negInf2D() {
        return new Vector2d(NEG_INF_2D);
    }
    
    public static Vector2f negInf2F() {
        return new Vector2f(NEG_INF_2F);
    }
    
    public static Vector3d negInf3D() {
        return new Vector3d(NEG_INF_3D);
    }
    
    public static Vector3f negInf3F() {
        return new Vector3f(NEG_INF_3F);
    }
    
    public static Vector2d zero2D() {
        return new Vector2d(ZERO_2D);
    }
    
    public static Vector2f zero2F() {
        return new Vector2f(ZERO_2F);
    }
    
    public static Vector2i zero2I() {
        return new Vector2i(ZERO_2I);
    }
    
    public static Vector3d zero3D() {
        return new Vector3d(ZERO_3D);
    }
    
    public static Vector3f zero3F() {
        return new Vector3f(ZERO_3F);
    }
    
    public static Vector3i zero3I() {
        return new Vector3i(ZERO_3I);
    }
}