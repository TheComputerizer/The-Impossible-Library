package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;

import static java.lang.Math.PI;

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
     * Formula: n!/(r!(n-r)!)
     * If n is negative, r is negative or r>n 0 will be returned
     */
    public static int combination(int n, int r) {
        if(r<0 || r>n) {
            TILRef.logWarn("Tried to calculate combination with incorrect inputs: {}!/({}!({}-{})!)",n,r,n,r);
            return 0;
        }
        return factorial(n)/(factorial(r)*factorial(n-r));
    }
    
    public static double cosFromSin(double sin, double angle) {
        double cos = Math.sqrt(1d-sin*sin);
        double a = angle+(PI/2d);
        double pi2 = PI*2d;
        double b = a-(int)(a/pi2)*pi2;
        if(b<0d) b = pi2+b;
        return b>=PI ? -cos : cos;
    }
    
    /**
     * Distance of N-Dimensional side lengths
     */
    public static double distance(double ... values) {
        double total = 0d;
        for(double value : values) total+=(value*value);
        return Math.sqrt(total);
    }
    
    /**
     * Returns num!
     * Negative num values are undefined (1/0), but 0 will be returned instead
     */
    public static int factorial(int num) {
        if(num<0) {
            TILRef.logError("Tried to calculate factorial with incorrect input: {}!",num);
            return 0;
        }
        int prod = 1;
        for(int i=num;i>1;i--) prod*=i;
        return prod;
    }
    
    /**
     * Ensures the angle is within the range (-RADIANS_180, RADIANS_180]
     */
    public static double getBoundedAngle(double angle) {
        while(angle>RADIANS_180) angle-=RADIANS_360;
        while(angle<-RADIANS_180) angle+=RADIANS_360;
        return angle;
    }
    
    /**
     * Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static double getHalfwayD(Vector2 vec) {
        return getHalfway(vec.dX(),vec.dY());
    }

    /**
     * Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static float getHalfwayF(Vector2 vec) {
        return getHalfway(vec.fX(),vec.fY());
    }

    /**
     * Calculates a 1D position halfway in between a given start and end
     */
    public static double getHalfway(double start, double end) {
        return Math.min(start,end)+(Math.abs(end-start)/2d);
    }
    
    /**
     * Calculates a 1D position halfway in between a given start and end
     */
    public static float getHalfway(float start, float end) {
        return Math.min(start,end)+(Math.abs(end-start)/2f);
    }
    
    /**
     * Formula: n!/(n-r)!
     * If n is negative, r is negative or r>n 0 will be returned
     */
    public static int permutation(int n, int r) {
        if(r<0 || r>n) {
            TILRef.logWarn("Tried to calculate permutation with incorrect inputs: {}!/({}-{})!",n,n,r);
            return 0;
        }
        return factorial(n)/factorial(n-r);
    }
}