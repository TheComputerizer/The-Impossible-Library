package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;


import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * This is basically just a better implementation of Map<Vector3,Collection<Tuple<Vector3,Vector3>>>
 */
public class TriangleMapper {

    @Getter private final Vector3 original;
    private final Vector3[] pairA;
    private final Vector3[] pairB;
    public final int length;

    public TriangleMapper(Vector3 original, Vector3... otherVectors) {
        this.original = original;
        Vector3[] potentialClosest = findClosest(otherVectors);
        this.length = potentialClosest.length-1;
        this.pairA = new Vector3[this.length];
        this.pairB = new Vector3[this.length];
        calculatePairs(potentialClosest);
    }

    private Vector3[] findClosest(Vector3 ... otherVectors) {
        List<Vector3> firstClosest = new ArrayList<>();
        List<Vector3> secondClosest = new ArrayList<>();
        double firstDist = Double.MAX_VALUE;
        double secondDist = Double.MAX_VALUE;
        for(Vector3 otherVec : otherVectors) {
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
        return firstClosest.toArray(new Vector3[0]);
    }

    private void calculatePairs(Vector3 ... potentialClosest) {
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
    
    public Vector3 getA(int index) {
        return this.pairA[index];
    }

    public Vector3 getB(int index) {
        return this.pairB[index];
    }
}