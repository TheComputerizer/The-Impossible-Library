package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

@SuppressWarnings("unused")
public class VectorHelper {
    
    /**
     These are all private to prevent accidental modification of data.
     The methods below should be called instead to create copies.
     */
    private static final Vector2 INF_2D = new Vector2Final(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    private static final Vector2 INF_2F = new Vector2Final(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
    private static final Vector3 INF_3D = new Vector3Final(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private static final Vector3 INF_3F = new Vector3Final(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    private static final Vector4 INF_4D = new Vector4Final(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    private static final Vector4 INF_4F = new Vector4Final(Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY,Float.POSITIVE_INFINITY);
    private static final Vector2 MAX_2D = new Vector2Final(Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector2 MAX_2F = new Vector2Final(Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector2 MAX_2I = new Vector2Final(Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector3 MAX_3D = new Vector3Final(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector3 MAX_3F = new Vector3Final(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector3 MAX_3I = new Vector3Final(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector4 MAX_4D = new Vector4Final(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE);
    private static final Vector4 MAX_4F = new Vector4Final(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE);
    private static final Vector4 MAX_4I = new Vector4Final(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    private static final Vector2 MIN_2D = new Vector2Final(Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector2 MIN_2F = new Vector2Final(Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector2 MIN_2I = new Vector2Final(Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector3 MIN_3D = new Vector3Final(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector3 MIN_3F = new Vector3Final(Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector3 MIN_3I = new Vector3Final(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector4 MIN_4D = new Vector4Final(Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE);
    private static final Vector4 MIN_4F = new Vector4Final(Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE,Float.MIN_VALUE);
    private static final Vector4 MIN_4I = new Vector4Final(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
    private static final Vector2 NEG_INF_2D = new Vector2Final(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector2 NEG_INF_2F = new Vector2Final(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector3 NEG_INF_3D = new Vector3Final(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector3 NEG_INF_3F = new Vector3Final(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector4 NEG_INF_4D = new Vector4Final(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY);
    private static final Vector4 NEG_INF_4F = new Vector4Final(Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY,Float.NEGATIVE_INFINITY);
    private static final Vector2 ZERO_2D = new Vector2Final(0d,0d);
    private static final Vector2 ZERO_2F = new Vector2Final(0f,0f);
    private static final Vector2 ZERO_2I = new Vector2Final(0,0);
    private static final Vector3 ZERO_3D = new Vector3Final(0d,0d,0d);
    private static final Vector3 ZERO_3F = new Vector3Final(0f,0f,0f);
    private static final Vector3 ZERO_3I = new Vector3Final(0,0,0);
    private static final Vector4 ZERO_4D = new Vector4Final(0d,0d,0d,0d);
    private static final Vector4 ZERO_4F = new Vector4Final(0f,0f,0f,0f);
    private static final Vector4 ZERO_4I = new Vector4Final(0,0,0,0);
    
    public static Vector3 from(Facing facing) {
        Vector3 axisDir = facing.getAxis().getDirection();
        return facing.isPositive() ? axisDir : axisDir.mulScalar(-1d);
    }
    
    public static Vector3 from(Axis axis) {
        return axis.getDirection();
    }
    
    /**
     * Gets a vector in the center of the two inputs.
     * Assumes the min and max have already been properly calculated.
     */
    public static Vector3 getCenter(Vector3 min, Vector3 max) {
        return new Vector3(min.dX()+(max.dX()-min.dX())/2d,min.dY()+(max.dY()-min.dY())/2d,min.dZ()+(max.dZ()-min.dZ())/2d);
    }
    
    public static Vector2 inf2D() {
        return INF_2D.copy();
    }
    
    public static Vector2 inf2F() {
        return INF_2F.copy();
    }
    
    public static Vector3 inf3D() {
        return INF_3D.copy();
    }
    
    public static Vector3 inf3F() {
        return INF_3F.copy();
    }
    
    public static Vector4 inf4D() {
        return INF_4D.copy();
    }
    
    public static Vector4 inf4F() {
        return INF_4F.copy();
    }
    
    public static Vector2 max2D() {
        return MAX_2D.copy();
    }
    
    public static Vector2 max2F() {
        return MAX_2F.copy();
    }
    
    public static Vector2 max2I() {
        return MAX_2I.copy();
    }
    
    public static Vector3 max3D() {
        return MAX_3D.copy();
    }
    
    public static Vector3 max3F() {
        return MAX_3F.copy();
    }
    
    public static Vector3 max3I() {
        return MAX_3I.copy();
    }
    
    public static Vector4 max4D() {
        return MAX_4D.copy();
    }
    
    public static Vector4 max4F() {
        return MAX_4F.copy();
    }
    
    public static Vector4 max4I() {
        return MAX_4I.copy();
    }
    
    public static Vector2 min2D() {
        return MIN_2D.copy();
    }
    
    public static Vector2 min2F() {
        return MIN_2F.copy();
    }
    
    public static Vector2 min2I() {
        return MIN_2I.copy();
    }
    
    public static Vector3 min3D() {
        return MIN_3D.copy();
    }
    
    public static Vector3 min3F() {
        return MIN_3F.copy();
    }
    
    public static Vector3 min3I() {
        return MIN_3I.copy();
    }
    
    public static Vector4 min4D() {
        return MIN_4D.copy();
    }
    
    public static Vector4 min4F() {
        return MIN_4F.copy();
    }
    
    public static Vector4 min4I() {
        return MIN_4I.copy();
    }
    
    public static Vector2 negInf2D() {
        return NEG_INF_2D.copy();
    }
    
    public static Vector2 negInf2F() {
        return NEG_INF_2F.copy();
    }
    
    public static Vector3 negInf3D() {
        return NEG_INF_3D.copy();
    }
    
    public static Vector3 negInf3F() {
        return NEG_INF_3F.copy();
    }
    
    public static Vector4 negInf4D() {
        return NEG_INF_4D.copy();
    }
    
    public static Vector4 negInf4F() {
        return NEG_INF_4F.copy();
    }
    
    public static Vector2 randomD(Vector2 min, Vector2 max) {
        return new Vector2(RandomHelper.randomDouble(min.dX(),max.dX()),RandomHelper.randomDouble(min.dY(),max.dY()));
    }
    
    public static Vector3 randomD(Vector3 min, Vector3 max) {
        return new Vector3(RandomHelper.randomDouble(min.dX(),max.dX()),RandomHelper.randomDouble(min.dY(),max.dY()),
                            RandomHelper.randomDouble(min.dZ(),max.dZ()));
    }
    
    public static Vector4 randomD(Vector4 min, Vector4 max) {
        return new Vector4(RandomHelper.randomDouble(min.dX(),max.dX()),RandomHelper.randomDouble(min.dY(),max.dY()),
                           RandomHelper.randomDouble(min.dZ(),max.dZ()),RandomHelper.randomDouble(min.dW(),max.dW()));
    }
    
    public static Vector2 randomF(Vector2 min, Vector2 max) {
        return new Vector2(RandomHelper.randomFloat(min.fX(),max.fX()),RandomHelper.randomFloat(min.fY(),max.fY()));
    }
    
    public static Vector3 randomF(Vector3 min, Vector3 max) {
        return new Vector3(RandomHelper.randomFloat(min.fX(),max.fX()),RandomHelper.randomFloat(min.fY(),max.fY()),
                            RandomHelper.randomFloat(min.fZ(),max.fZ()));
    }
    
    public static Vector4 randomF(Vector4 min, Vector4 max) {
        return new Vector4(RandomHelper.randomFloat(min.fX(),max.fX()),RandomHelper.randomFloat(min.fY(),max.fY()),
                           RandomHelper.randomFloat(min.fZ(),max.fZ()),RandomHelper.randomFloat(min.fW(),max.fW()));
    }
    
    public static Vector2 randomI(Vector2 min, Vector2 max) {
        return new Vector2(RandomHelper.randomInt(min.iX(),max.iX()),RandomHelper.randomInt(min.iY(),max.iY()));
    }
    
    public static Vector3 randomI(Vector3 min, Vector3 max) {
        return new Vector3(RandomHelper.randomInt(min.iX(),max.iX()),RandomHelper.randomInt(min.iY(),max.iY()),
                            RandomHelper.randomInt(min.iZ(),max.iZ()));
    }
    
    public static Vector4 randomI(Vector4 min, Vector4 max) {
        return new Vector4(RandomHelper.randomInt(min.iX(),max.iX()),RandomHelper.randomInt(min.iY(),max.iY()),
                           RandomHelper.randomInt(min.iZ(),max.iZ()),RandomHelper.randomInt(min.iW(),max.iW()));
    }
    
    /**
     Assumes the input vector is formatted as (radius, angle) where the angle is in radians.
     */
    public static Vector2 toCartesian(Vector2 vec) {
        return new Vector2(Math.cos(vec.dY())*vec.dX(),Math.sin(vec.dY())*vec.dX());
    }
    
    /**
     The angle must be in radians.
     */
    public static Vector2 toCartesian(double radius, double angle) {
        return new Vector2(Math.cos(angle)*radius,Math.sin(angle)*radius);
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     */
    public static Vector2 toPolar(Vector2 vec) {
        return new Vector2(Math.sqrt(Math.pow(vec.dX(),2)+Math.pow(vec.dY(),2)),Math.atan2(vec.dY(),vec.dX()));
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     */
    public static Vector2 toPolar(double x, double y) {
        return new Vector2(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)),Math.atan2(y,x));
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     The offset will be added to the resulting angle and as such must be in radians.
     */
    public static Vector2 toPolar(Vector2 vec, double offset) {
        return new Vector2(Math.sqrt(Math.pow(vec.dX(),2)+Math.pow(vec.dY(),2)),Math.atan2(vec.dY(),vec.dX())+offset);
    }
    
    /**
     Returns a vector of (radius, angle) where the angle is in radians.
     The offset will be added to the resulting angle and as such must be in radians.
     */
    public static Vector2 toPolar(double x, double y, double offset) {
        return new Vector2(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)),Math.atan2(y,x)+offset);
    }
    
    public static Vector2 zero2D() {
        return ZERO_2D.copy();
    }
    
    public static Vector2 zero2F() {
        return ZERO_2F.copy();
    }
    
    public static Vector2 zero2I() {
        return ZERO_2I.copy();
    }
    
    public static Vector3 zero3D() {
        return ZERO_3D.copy();
    }
    
    public static Vector3 zero3F() {
        return ZERO_3F.copy();
    }
    
    public static Vector3 zero3I() {
        return ZERO_3I.copy();
    }
    
    public static Vector4 zero4D() {
        return ZERO_4D.copy();
    }
    
    public static Vector4 zero4F() {
        return ZERO_4F.copy();
    }
    
    public static Vector4 zero4I() {
        return ZERO_4I.copy();
    }
}