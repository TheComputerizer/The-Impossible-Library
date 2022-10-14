package mods.thecomputerizer.theimpossiblelibrary.util;

import javax.vecmath.Vector2f;

public class MathUtil {
    public static final double CIRCLE_RADIANS = Math.toRadians(360);
    public static final double HALF_CIRCLE_RADIANS = Math.toRadians(180);

    /*
        Gets the distance between 2D coordinate points
     */
    public static double distance(Vector2f vec1, Vector2f vec2) {
        return Math.sqrt(Math.pow(vec1.x-vec2.x,2)+Math.pow(vec1.y-vec2.y,2));
    }

    /*
        Helper math for radial gui buttons
        Checks whether a given screen position is within the bounds of a circle
        The input radius is a vector where radius.x is the inner radius and radius.y is the outer radius
     */
    public static boolean isInCircle(Vector2f center, Vector2f pos, Vector2f radius) {
        double distance = distance(center, pos);
        return distance>=radius.x && distance<=radius.y;
    }

    /*
        Translates the input angles so they are within the bounds of 0 to 2*PI radians
        The input angles must be in radians
     */
    public static Vector2f transformAngleBoundsIfNeeded(float angleStart, float angleEnd) {
        if(angleStart-angleEnd>=CIRCLE_RADIANS) new Vector2f(0f,(float)CIRCLE_RADIANS);
        if(angleStart<0) {
            while (angleStart < 0) {
                angleStart += CIRCLE_RADIANS;
                angleEnd += CIRCLE_RADIANS;
            }
        }
        else {
            while (angleStart > CIRCLE_RADIANS) {
                angleStart -= CIRCLE_RADIANS;
                angleEnd -= CIRCLE_RADIANS;
            }
        }
        return new Vector2f(angleStart,angleEnd);
    }

    /*
        Helper math for radial gui buttons
        Checks whether a given screen position is within the bounds of a slice of a circle
        The input angles must be in radians
        The input radius is a vector where radius.x is the inner radius and radius.y is the outer radius
     */
    public static boolean isInCircleSlice(Vector2f center, Vector2f pos, Vector2f radius, float angleStart, float angleEnd) {
        if(!isInCircle(center, pos, radius)) return false;
        float x = center.x-pos.x;
        float y = center.y-pos.y;
        double angle = Math.atan(y/x);
        Vector2f angleVec = transformAngleBoundsIfNeeded(angleStart, angleEnd);
        return angle>=(angleVec.x+HALF_CIRCLE_RADIANS) && angle<(angleVec.y+HALF_CIRCLE_RADIANS);
    }

    /*
        Helper math for radial gui elements
        Calculates a vertex to be pushed into a BufferBuilder using the center of the circle and the given radius and angle
     */
    public static Vector2f getVertex(Vector2f center, float radius, float angle) {
        return new Vector2f(center.x+(radius*(float)Math.cos(angle)),center.y+(radius*(float)Math.sin(angle)));
    }

    /*
        Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static float getHalfway(Vector2f vec) {
        return getHalfway(vec.x, vec.y);
    }

    /*
        Calculates a 1D position halfway in between a given start and end
     */
    public static float getHalfway(float start, float end) {
        return start+((end-start)/2f);
    }
}
