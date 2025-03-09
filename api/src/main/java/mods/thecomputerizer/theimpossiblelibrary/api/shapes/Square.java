package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;

import java.util.Objects;

@Getter
public class Square extends Plane {
    
    protected double sideLength;
    protected double heightRatio;
    
    /**
     See ShapeHelper for alternative construction methods
     */
    public Square(Vector3 direction, double length, double heightRatio) {
        super(direction,VectorHelper.zero2D(),VectorHelper.zero2D());
        setSideLength(length,heightRatio);
    }
    
    @Override public Square copy() {
        return getScaled(1d);
    }
    
    @Override public boolean equals(Object other) {
        if(this==other) return true;
        if(Objects.isNull(other)) return false;
        if(other.getClass()==Square.class) {
            Square square = (Square)other;
            return sameDirection(square) && this.sideLength==square.sideLength && this.heightRatio==square.heightRatio;
        }
        return false;
    }
    
    @Override public Square getScaled(double scale) {
        if(scale<=0) scale = 1d;
        return new Square(new Vector3(this.direction),this.sideLength*scale,this.heightRatio);
    }
    
    @Override public Square getScaled(Vector2 scale) {
        return getScaled(scale.dX(),scale.dY());
    }
    
    @Override public Square getScaled(double scaleX, double scaleY) {
        return getScaled(scaleX==1d ? scaleY : scaleX);
    }
    
    @Override public Square getScaled(Vector3 scale) {
        return getScaled(scale.dX(),scale.dY(),scale.dZ());
    }
    
    @Override public Square getScaled(double scaleX, double scaleY, double scaleZ) {
        return getScaled(scaleX==1d ? (scaleY==1d ? scaleZ : scaleY) : scaleX);
    }
    
    @Override public void setRelativeMax(Vector2 max) {}
    
    @Override public void setRelativeMin(Vector2 min) {}
    
    public void setSideLength(double length, double heightRatio) {
        this.sideLength = length;
        this.heightRatio = heightRatio;
        double halfLenX = Math.abs(length*Math.min(heightRatio,1d))/2d;
        double halfLenY = Math.abs(length*Math.min(1d/heightRatio,1d))/2d;
        this.relativeMax = new Vector2(halfLenX,halfLenY);
        this.relativeMin = new Vector2(-halfLenX,-halfLenY);
        this.worldMin = getWorldCoordinate(this.relativeMin);
        this.worldMax = getWorldCoordinate(this.relativeMax);
        calculateSize();
    }
}