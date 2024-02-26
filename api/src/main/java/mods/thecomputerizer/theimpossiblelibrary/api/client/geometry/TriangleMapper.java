package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;


import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * This is basically just a better implementation of Map<Vector3d,Collection<Tuple<Vector3d,Vector3d>>>
 */
public class TriangleMapper {

    private final Vector3d original;
    private final Vector3d[] pairA;
    private final Vector3d[] pairB;
    public final int length;

    public TriangleMapper(Vector3d original, Vector3d ... otherVectors) {
        this.original = original;
        Vector3d[] potentialClosest = findClosest(otherVectors);
        this.length = potentialClosest.length-1;
        this.pairA = new Vector3d[this.length];
        this.pairB = new Vector3d[this.length];
        calculatePairs(potentialClosest);
    }

    private Vector3d[] findClosest(Vector3d ... otherVectors) {
        List<Vector3d> firstClosest = new ArrayList<>();
        List<Vector3d> secondClosest = new ArrayList<>();
        double firstDist = Double.MAX_VALUE;
        double secondDist = Double.MAX_VALUE;
        for(Vector3d otherVec : otherVectors) {
            if(otherVec!=this.original) {
                double distance = this.original.distance(otherVec);
                if(firstDist==Double.MAX_VALUE || isCloseEnough(firstDist,distance)) {
                    firstClosest.add(otherVec);
                    firstDist = distance;
                } else if(secondDist==Double.MAX_VALUE || isCloseEnough(secondDist,distance)) {
                    secondClosest.add(otherVec);
                    secondDist = distance;
                }
            }
        }
        if(firstClosest.size()<2) firstClosest.addAll(secondClosest);
        return firstClosest.toArray(new Vector3d[0]);
    }

    private void calculatePairs(Vector3d ... potentialClosest) {
        for(int i=0; i<potentialClosest.length-1; i++) {
            if(i==potentialClosest.length-2) {
                this.pairA[i] = potentialClosest[i];
                this.pairB[i] = potentialClosest[i+1];
            } else {
                int match = i+1;
                double minDist = Double.MAX_VALUE;
                for(int j=i+1; j<potentialClosest.length; j++) {
                    double distance = potentialClosest[i].distance(potentialClosest[j]);
                    if(minDist==Double.MAX_VALUE || isCloseEnough(minDist,distance)) {
                        match = j;
                        minDist = distance;
                    }
                }
                this.pairA[i] = potentialClosest[i];
                this.pairB[i] = potentialClosest[match];
            }
        }
    }

    private boolean isCloseEnough(double min, double distance) {
        return ((int)(distance*200d))<=((int)(min*200d));
    }

    public Vector3d getOriginal() {
        return this.original;
    }

    public Vector3d getA(int index) {
        return this.pairA[index];
    }

    public Vector3d getB(int index) {
        return this.pairB[index];
    }
}