package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.joml.Vector4i;

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
    private static final Vector4d INF_4D = new Vector4d(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    private static final Vector4f INF_4F = new Vector4f(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
    private static final Vector2d MAX_2D = new Vector2d(Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector2f MAX_2F = new Vector2f(Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector2i MAX_2I = new Vector2i(Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector3d MAX_3D = new Vector3d(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector3f MAX_3F = new Vector3f(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector3i MAX_3I = new Vector3i(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector4d MAX_4D = new Vector4d(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector4f MAX_4F = new Vector4f(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector4i MAX_4I = new Vector4i(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector2d MIN_2D = new Vector2d(Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector2f MIN_2F = new Vector2f(Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector2i MIN_2I = new Vector2i(Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector3d MIN_3D = new Vector3d(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector3f MIN_3F = new Vector3f(Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector3i MIN_3I = new Vector3i(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector4d MIN_4D = new Vector4d(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector4f MIN_4F = new Vector4f(Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector4i MIN_4I = new Vector4i(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector2d NEG_INF_2D = new Vector2d(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector2f NEG_INF_2F = new Vector2f(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector3d NEG_INF_3D = new Vector3d(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector3f NEG_INF_3F = new Vector3f(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector4d NEG_INF_4D = new Vector4d(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector4f NEG_INF_4F = new Vector4f(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector2d ZERO_2D = new Vector2d(0d,0d);
    private static final Vector2f ZERO_2F = new Vector2f(0f,0f);
    private static final Vector2i ZERO_2I = new Vector2i(0,0);
    private static final Vector3d ZERO_3D = new Vector3d(0d,0d,0d);
    private static final Vector3f ZERO_3F = new Vector3f(0f,0f,0f);
    private static final Vector3i ZERO_3I = new Vector3i(0,0,0);
    private static final Vector4d ZERO_4D = new Vector4d(0d,0d,0d,0d);
    private static final Vector4f ZERO_4F = new Vector4f(0f,0f,0f,0f);
    private static final Vector4i ZERO_4I = new Vector4i(0,0,0,0);
    
    public static Vector2d copy2D(Vector2d v) {
        return new Vector2d(v.x,v.y);
    }
    
    public static Vector2f copy2F(Vector2f v) {
        return new Vector2f(v.x,v.y);
    }
    
    public static Vector2i copy2I(Vector2i v) {
        return new Vector2i(v.x,v.y);
    }
    
    public static Vector3d copy3D(Vector3d v) {
        return new Vector3d(v.x,v.y,v.z);
    }
    
    public static Vector3f copy3F(Vector3f v) {
        return new Vector3f(v.x,v.y,v.z);
    }
    
    public static Vector3i copy3I(Vector3i v) {
        return new Vector3i(v.x,v.y,v.z);
    }
    
    public static Vector4d copy4D(Vector4d v) {
        return new Vector4d(v.x,v.y,v.z,v.w);
    }
    
    public static Vector4f copy4F(Vector4f v) {
        return new Vector4f(v.x,v.y,v.z,v.w);
    }
    
    public static Vector4i copy4I(Vector4i v) {
        return new Vector4i(v.x,v.y,v.z,v.w);
    }
    
    public static Vector3d from(Facing facing) {
        Vector3d axisDir = facing.getAxis().getDirection();
        return facing.isPositive() ? axisDir : axisDir.mul(-1d);
    }
    
    public static Vector3d from(Axis axis) {
        return axis.getDirection();
    }
    
    /**
     * Gets a vector in the center of the two inputs.
     * Assumes the min and max have already been properly calculated.
     */
    public static Vector3d getCenter(Vector3d min, Vector3d max) {
        return new Vector3d(min.x+(max.x-min.x)/2d,min.y+(max.y-min.y)/2d,min.z+(max.z-min.z)/2d);
    }
    
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
    
    public static Vector4d inf4D() {
        return new Vector4d(INF_4D);
    }
    
    public static Vector4f inf4F() {
        return new Vector4f(INF_4F);
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
    
    public static Vector4d max4D() {
        return new Vector4d(MAX_4D);
    }
    
    public static Vector4f max4F() {
        return new Vector4f(MAX_4F);
    }
    
    public static Vector4i max4I() {
        return new Vector4i(MAX_4I);
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
    
    public static Vector4d min4D() {
        return new Vector4d(MIN_4D);
    }
    
    public static Vector4f min4F() {
        return new Vector4f(MIN_4F);
    }
    
    public static Vector4i min4I() {
        return new Vector4i(MIN_4I);
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
    
    public static Vector4d negInf4D() {
        return new Vector4d(NEG_INF_4D);
    }
    
    public static Vector4f negInf4F() {
        return new Vector4f(NEG_INF_4F);
    }
    
    public static Vector2d randomD(Vector2d min, Vector2d max) {
        return new Vector2d(RandomHelper.randomDouble(min.x,max.x),RandomHelper.randomDouble(min.y,max.y));
    }
    
    public static Vector3d randomD(Vector3d min, Vector3d max) {
        return new Vector3d(RandomHelper.randomDouble(min.x,max.x),RandomHelper.randomDouble(min.y,max.y),
                            RandomHelper.randomDouble(min.z,max.z));
    }
    
    public static Vector4d randomD(Vector4d min, Vector4d max) {
        return new Vector4d(RandomHelper.randomDouble(min.x, max.x),RandomHelper.randomDouble(min.y,max.y),
                            RandomHelper.randomDouble(min.z,max.z),RandomHelper.randomDouble(min.w,max.w));
    }
    
    public static Vector2f randomF(Vector2f min, Vector2f max) {
        return new Vector2f(RandomHelper.randomFloat(min.x,max.x),RandomHelper.randomFloat(min.y,max.y));
    }
    
    public static Vector3f randomF(Vector3f min, Vector3f max) {
        return new Vector3f(RandomHelper.randomFloat(min.x,max.x),RandomHelper.randomFloat(min.y,max.y),
                            RandomHelper.randomFloat(min.z,max.z));
    }
    
    public static Vector4f randomF(Vector4f min, Vector4f max) {
        return new Vector4f(RandomHelper.randomFloat(min.x, max.x), RandomHelper.randomFloat(min.y,max.y),
                            RandomHelper.randomFloat(min.z,max.z),RandomHelper.randomFloat(min.w,max.w));
    }
    
    public static Vector2i randomI(Vector2i min, Vector2i max) {
        return new Vector2i(RandomHelper.randomInt(min.x,max.x),RandomHelper.randomInt(min.y,max.y));
    }
    
    public static Vector3i randomI(Vector3i min, Vector3i max) {
        return new Vector3i(RandomHelper.randomInt(min.x,max.x),RandomHelper.randomInt(min.y,max.y),
                            RandomHelper.randomInt(min.z,max.z));
    }
    
    public static Vector4i randomI(Vector4i min, Vector4i max) {
        return new Vector4i(RandomHelper.randomInt(min.x, max.x), RandomHelper.randomInt(min.y,max.y),
                            RandomHelper.randomInt(min.z,max.z),RandomHelper.randomInt(min.w,max.w));
    }
    
    public static double[] toArray(Vector2d vector) {
        return new double[]{vector.x,vector.y};
    }
    
    public static double[] toArray(Vector3d vector) {
        return new double[]{vector.x,vector.y,vector.z};
    }
    
    public static double[] toArray(Vector4d vector) {
        return new double[]{vector.x,vector.y,vector.z,vector.w};
    }
    
    public static float[] toArray(Vector2f vector) {
        return new float[]{vector.x,vector.y};
    }
    
    public static float[] toArray(Vector3f vector) {
        return new float[]{vector.x,vector.y,vector.z};
    }
    
    public static float[] toArray(Vector4f vector) {
        return new float[]{vector.x,vector.y,vector.z,vector.w};
    }
    
    public static int[] toArray(Vector2i vector) {
        return new int[]{vector.x,vector.y};
    }
    
    public static int[] toArray(Vector3i vector) {
        return new int[]{vector.x,vector.y,vector.z};
    }
    
    public static int[] toArray(Vector4i vector) {
        return new int[]{vector.x,vector.y,vector.z,vector.w};
    }
    
    /**
     Assumes the input vector is formatted as (radius, angle) where the angle is in radians.
     */
    public static Vector2d toCartesian(Vector2d vec) {
        return new Vector2d(Math.cos(vec.y)*vec.x,Math.sin(vec.y)*vec.x);
    }
    
    /**
     The angle must be in radians.
     */
    public static Vector2d toCartesian(double radius, double angle) {
        return new Vector2d(Math.cos(angle)*radius,Math.sin(angle)*angle);
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     */
    public static Vector2d toPolar(Vector2d vec) {
        return new Vector2d(Math.sqrt(Math.pow(vec.x,2)+Math.pow(vec.y,2)),Math.atan2(vec.y,vec.x));
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     */
    public static Vector2d toPolar(double x, double y) {
        return new Vector2d(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)),Math.atan2(y,x));
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     The offset will be added to the resulting angle and as such must be in radians.
     */
    public static Vector2d toPolar(Vector2d vec, double offset) {
        return new Vector2d(Math.sqrt(Math.pow(vec.x,2)+Math.pow(vec.y,2)),Math.atan2(vec.y,vec.x)+offset);
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     The offset will be added to the resulting angle and as such must be in radians.
     */
    public static Vector2d toPolar(double x, double y, double offset) {
        return new Vector2d(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)),Math.atan2(y,x)+offset);
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
    
    public static Vector4d zero4D() {
        return new Vector4d(ZERO_4D);
    }
    
    public static Vector4f zero4F() {
        return new Vector4f(ZERO_4F);
    }
    
    public static Vector4i zero4I() {
        return new Vector4i(ZERO_4I);
    }
}