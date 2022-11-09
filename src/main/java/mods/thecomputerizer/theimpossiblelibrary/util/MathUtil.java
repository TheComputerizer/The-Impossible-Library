package mods.thecomputerizer.theimpossiblelibrary.util;

import javax.vecmath.*;

public class MathUtil {
    public static final double CIRCLE_RADIANS = Math.toRadians(360);
    public static final double HALF_CIRCLE_RADIANS = Math.toRadians(180);

    /*
        Gets the distance between 2D coordinate points
     */
    public static double distance(Tuple2f vec1, Tuple2f vec2) {
        return Math.sqrt(Math.pow(vec1.x-vec2.x,2)+Math.pow(vec1.y-vec2.y,2));
    }

    /*
        Helper math for radial gui buttons
        Checks whether a given screen position is within an outer radius but not within an inner radius of a circle
        The input radius is a tuple where radius.x is the inner radius and radius.y is the outer radius
     */
    public static boolean isInCircle(Tuple2i center, double distance, Tuple2i radius) {
        return distance>radius.x && distance<=radius.y;
    }

    /*
        Helper math for radial gui buttons
        Checks whether a given screen position is within the bounds of a circle
     */
    public static boolean isInCircle(Tuple2i center, double distance, float outerRadius) {
        return distance<=outerRadius;
    }

    /*
        Helper math for radial gui elements
        Calculates a tuple to be pushed into a BufferBuilder using the center of the circle and the given radius and angle
     */
    public static Point2i getVertex(Point2i center, float radius, float angle) {
        return new Point2i((int) (center.x+(radius*(float)Math.cos(angle))),(int) (center.y+(radius*(float)Math.sin(angle))));
    }

    /*
        returns a tuple of the angle bounds of a circle slice given the index of the slice and total number of slices
     */
    public static Point2f makeAngleTuple(int index, int numSlices) {
        float startAngle = (((index - 0.5f) / numSlices) + 0.25f) * 360;
        float endAngle = (((index + 0.5f) / numSlices) + 0.25f) * 360;
        return new Point2f(startAngle,endAngle);
    }

    /*
        returns a tuple of the angle bounds for a radial progress bar given a percentage progress
     */
    public static Point2f progressAngles(float progress) {
        return new Point2f(0f,360*progress);
    }

    /*
        converts angle bounds stored in a tuple as degrees to radians
     */
    public static Point2f toRadians(Point2f degreeVec) {
        return new Point2f((float)Math.toRadians(degreeVec.x),(float)Math.toRadians(degreeVec.y));
    }

    /*
        Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static float getHalfway(Tuple2f vec) {
        return getHalfway(vec.x, vec.y);
    }

    /*
        Calculates a 1D position halfway in between a given start and end
     */
    public static float getHalfway(float start, float end) {
        return start+((end-start)/2f);
    }

    /*
        Calculates the center position of a circle give the start and end angles,
        inner and outer radius, relative center position, and total number of slices
        Angles must be in degrees
     */
    public static Point2i getCenterPosOfSlice(Point2f angles, Tuple2i radius, Tuple2i center, int numSlices) {
        float centerAngle;
        if(numSlices>1) centerAngle = (float) Math.toRadians(getHalfway(angles.x,angles.y));
        else centerAngle = (float)Math.toRadians(90d);
        float relativeRadius = radius.y-((radius.y-radius.x)*0.4f);
        return new Point2i((int) (center.x+relativeRadius*Math.cos(centerAngle)),
                (int) (center.y+relativeRadius*Math.sin(centerAngle)));
    }
}
