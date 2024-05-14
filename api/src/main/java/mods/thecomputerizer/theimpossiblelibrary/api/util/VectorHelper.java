package mods.thecomputerizer.theimpossiblelibrary.api.util;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3i;

@SuppressWarnings("unused")
public class VectorHelper {
    
    public static final Vector2f MAX_2F = new Vector2f(Float.MAX_VALUE,Float.MAX_VALUE);
    public static final Vector2i MAX_2I = new Vector2i(Integer.MAX_VALUE,Integer.MAX_VALUE);
    public static final Vector3d MAX_3D = new Vector3d(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    public static final Vector3i MAX_3I = new Vector3i(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    public static final Vector2f MIN_2F = new Vector2f(Float.MIN_VALUE,Float.MIN_VALUE);
    public static final Vector2i MIN_2I = new Vector2i(Integer.MIN_VALUE,Integer.MIN_VALUE);
    public static final Vector3d MIN_3D = new Vector3d(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    public static final Vector3i MIN_3I = new Vector3i(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    public static final Vector2f ZERO_2F = new Vector2f(0,0);
    public static final Vector2i ZERO_2I = new Vector2i(0,0);
    public static final Vector3d ZERO_3D = new Vector3d(0d,0d,0d);
    public static final Vector3i ZERO_3I = new Vector3i(0,0,0);

    /**
     * Gets a vector in the center of the two inputs.
     * Assumes the min and max have already been properly calculated.
     */
    public static Vector3d getCenter(Vector3d min, Vector3d max) {
        return new Vector3d(min.x+(max.x-min.x)/2d,min.y+(max.y-min.y)/2d,min.z+(max.z-min.z)/2d);
    }
}