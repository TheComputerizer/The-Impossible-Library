package mods.thecomputerizer.theimpossiblelibrary.api.util;

import org.joml.Vector2d;
import org.joml.Vector2f;

@SuppressWarnings("unused")
public class MathHelper {

    public static final double RADIANS_360 = Math.toRadians(360d);
    public static final double RADIANS_270 = Math.toRadians(270d);
    public static final double RADIANS_180 = Math.toRadians(180d);
    public static final double RADIANS_90 = Math.toRadians(90d);
    public static final double RADIANS_60 = Math.toRadians(60d);
    public static final double RADIANS_45 = Math.toRadians(45d);
    public static final double RADIANS_30 = Math.toRadians(30d);
    
    public static double clamp(double val, double min, double max) {
        return Math.max(min,Math.min(val,max));
    }
    
    public static float clamp(float val, float min, float max) {
        return Math.max(min,Math.min(val,max));
    }
    
    public static int clamp(int val, int min, int max) {
        return Math.max(min,Math.min(val,max));
    }
    
    /**
     Ensures the angle is within the range (-RADIANS_180, RADIANS_180]
     */
    public static double getBoundedAngle(double angle) {
        while(angle>RADIANS_180) angle-=RADIANS_360;
        while(angle<-RADIANS_180) angle+=RADIANS_360;
        return angle;
    }
    
    /**
     Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static double getHalfway(Vector2d vec) {
        return getHalfway(vec.x(),vec.y());
    }

    /**
        Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static float getHalfway(Vector2f vec) {
        return getHalfway(vec.x(),vec.y());
    }

    /**
        Calculates a 1D position halfway in between a given start and end
     */
    public static double getHalfway(double start, double end) {
        return Math.min(start,end)+(Math.abs(end-start)/2d);
    }
    
    /**
     Calculates a 1D position halfway in between a given start and end
     */
    public static float getHalfway(float start, float end) {
        return Math.min(start,end)+(Math.abs(end-start)/2f);
    }
}