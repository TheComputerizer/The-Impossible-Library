package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import org.joml.Vector2d;
import org.joml.Vector3d;

@Getter
public class Square extends Plane {
    
    protected double sideLength;
    
    public Square(Vector3d direction, double length) {
        super(direction);
        setSideLength(length);
    }
    
    @Override public Square copy() {
        return getScaled(1d);
    }
    
    @Override protected void calculateSize() {
        this.sideLength = Math.abs(this.relativeMax.x-this.relativeMin.x);
        this.width = this.sideLength;
        this.height = this.sideLength;
    }
    
    @Override public Square getScaled(double scale) {
        if(scale<=0) scale = 1d;
        return new Square(new Vector3d(this.direction),this.sideLength*scale);
    }
    
    @Override public Square getScaled(Vector2d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Square getScaled(double scaleX, double scaleY) {
        return getScaled(scaleX==1d ? scaleY : scaleX);
    }
    
    @Override public Square getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y,scale.z);
    }
    
    @Override public Square getScaled(double scaleX, double scaleY, double scaleZ) {
        return getScaled(scaleX==1d ? (scaleY==1d ? scaleZ : scaleY) : scaleX);
    }
    
    protected double getShorterLength(Vector2d vec) {
        boolean x = Math.abs(vec.x)<=Math.abs(vec.y);
        return (x ? vec.x : vec.y)*2d;
    }
    
    @Override public void setRelativeMax(Vector2d max) {
        setSideLength(getShorterLength(max));
    }
    
    @Override public void setRelativeMin(Vector2d min) {
        setSideLength(getShorterLength(min));
    }
    
    public void setSideLength(double length) {
        double radius = Math.abs(length)/2d;
        this.relativeMax = new Vector2d(radius,radius);
        this.relativeMin = new Vector2d(-radius,-radius);
        this.worldMin = getWorldCoordinate(this.relativeMin);
        this.worldMax = getWorldCoordinate(this.relativeMax);
        calculateSize();
    }
}