package mods.thecomputerizer.theimpossiblelibrary.util;

import com.mojang.math.Vector3f;

public class MathUtil {
    public static final double CIRCLE_RADIANS = Math.toRadians(360);
    public static final double HALF_CIRCLE_RADIANS = Math.toRadians(180);

    /*
        Gets the distance between 2D coordinate points
     */
    public static double distance(Vector3f vec1, Vector3f vec2) {
        return Math.sqrt(Math.pow(vec1.x()-vec2.x(),2)+Math.pow(vec1.y()-vec2.y(),2));
    }

    /*
        Helper math for radial gui buttons
        Checks whether a given screen position is within an outer radius but not within an inner radius of a circle
        The input radius is a vector where radius.x is the inner radius and radius.y is the outer radius
     */
    public static boolean isInCircle(Vector3f center, double distance, Vector3f radius) {
        return distance>radius.x() && distance<=radius.y();
    }

    /*
        Helper math for radial gui buttons
        Checks whether a given screen position is within the bounds of a circle
     */
    public static boolean isInCircle(Vector3f center, double distance, float outerRadius) {
        return distance<=outerRadius;
    }

    /*
        Returns the angle between 2 coordinate points in degrees
     */
    public static double getAngle(Vector3f start, Vector3f end) {
        return Math.toDegrees(Math.atan2(start.y() - end.y(), start.x() - end.x()));
    }

    /*
        Helper math for radial gui elements
        Calculates a vector to be pushed into a BufferBuilder using the center of the circle and the given radius and angle
        The angle is in radians
     */
    public static Vector3f getVertex(Vector3f center, float radius, float angle) {
        return new Vector3f(center.x()+(radius*(float)Math.cos(angle)),center.y()+(radius*(float)Math.sin(angle)),0);
    }

    /*
        More precise version of the above method
     */
    public static Vector3f getVertex(Vector3f center, double radius, double angle) {
        return new Vector3f((float) (center.x()+(radius*Math.cos(angle))),(float) (center.y()+(radius*Math.sin(angle))),0);
    }

    /*
        returns a vector of the angle bounds of a circle slice given the index of the slice and total number of slices
     */
    public static Vector3f makeAngleVector(int index, int numSlices) {
        float startAngle = (((index - 0.5f) / numSlices) + 0.25f) * 360;
        float endAngle = (((index + 0.5f) / numSlices) + 0.25f) * 360;
        return new Vector3f(startAngle,endAngle,0);
    }

    /*
        returns a vector of the angle bounds for a radial progress bar given a percentage progress
     */
    public static Vector3f progressAngles(float progress) {
        return new Vector3f(0f,360*progress,0);
    }

    /*
        converts angle bounds stored in a vector as degrees to radians
     */
    public static Vector3f toRadians(Vector3f degreeVec) {
        return new Vector3f((float)Math.toRadians(degreeVec.x()),(float)Math.toRadians(degreeVec.y()),0);
    }

    /*
        Calculates a 1D position halfway in between a given start and end where vec.x is the start and vec.y is the end
     */
    public static float getHalfway(Vector3f vec) {
        return getHalfway(vec.x(), vec.y());
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
    public static Vector3f getCenterPosOfSlice(Vector3f angles, Vector3f radius, Vector3f center, int numSlices) {
        float centerAngle;
        if(numSlices>1) centerAngle = (float) Math.toRadians(getHalfway(angles.x(),angles.y()));
        else centerAngle = (float)Math.toRadians(90d);
        float relativeRadius = radius.y()-((radius.y()-radius.x())*0.5f);
        return new Vector3f((float) (center.x()+relativeRadius*Math.cos(centerAngle)),
                (float) (center.y()+relativeRadius*Math.sin(centerAngle)),0);
    }
}
