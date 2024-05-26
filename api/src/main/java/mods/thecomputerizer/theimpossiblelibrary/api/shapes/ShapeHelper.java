package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Circle.CircleSlice;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

/**
 Way too many helper methods? Perhaps, but at least they aren't layered too deeply.
 */
@SuppressWarnings("unused")
public class ShapeHelper {
    
    public static Box box(double sideLength) {
        return box(-sideLength/2d,-sideLength/2d,-sideLength/2d,
                   sideLength/2d,sideLength/2d,sideLength/2d);
    }
    
    public static Box box(Vector3d center, double sideLength) {
        return box(center.x-sideLength/2d,center.y-sideLength/2d,center.z-sideLength/2d,
                   center.x+sideLength/2d,center.y+sideLength/2d,center.z+sideLength/2d);
    }
    
    public static Box box(Vector3d center, double sideLengthH, double sideLengthV) {
        return box(center.x-sideLengthH/2d,center.y-sideLengthV/2d,center.z-sideLengthH/2d,
                   center.x+sideLengthH/2d,center.y+sideLengthV/2d,center.z+sideLengthH/2d);
    }
    
    public static Box box(Vector3d center, double sideLengthX, double sideLengthY, double sideLengthZ) {
        return box(center.x-sideLengthX/2d,center.y-sideLengthY/2d,center.z-sideLengthZ/2d,
                   center.x+sideLengthX/2d,center.y+sideLengthY/2d,center.z+sideLengthZ/2d);
    }
    
    public static Box box(Vector3d v1, Vector3d v2) {
        return box(v1.x,v1.y,v1.z,v2.x,v2.y,v2.z);
    }
    
    public static Box box(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Box(x1,y1,z1,x2,y2,z2);
    }
    
    public static Circle circle(Facing facing, double heightRatio) {
        return circle(VectorHelper.from(facing),1d,0d,heightRatio,0d,0d);
    }
    
    public static Circle circle(Axis axis, double heightRatio) {
        return circle(VectorHelper.from(axis),1d,0d,heightRatio,0d,0d);
    }
    
    public static Circle circle(Vector3d direction, double heightRatio) {
        return circle(direction,1d,0d,heightRatio,0d,0d);
    }
    
    public static Circle circle(Facing facing, double radius, double heightRatio) {
        return circle(VectorHelper.from(facing),radius,0d,heightRatio,0d,0d);
    }
    
    public static Circle circle(Axis axis, double radius, double heightRatio) {
        return circle(VectorHelper.from(axis),radius,0d,heightRatio,0d,0d);
    }
    
    public static Circle circle(Vector3d direction, double radius, double heightRatio) {
        return circle(direction,radius,0d,heightRatio,0d,0d);
    }
    
    public static Circle circle(Facing facing, Vector2d radii, double heightRatio) {
        return circle(VectorHelper.from(facing),radii.x,radii.y,heightRatio,0d,0d);
    }
    
    public static Circle circle(Axis axis, Vector2d radii, double heightRatio) {
        return circle(VectorHelper.from(axis),radii.x,radii.y,heightRatio,0d,0d);
    }
    
    public static Circle circle(Vector3d direction, Vector2d radii, double heightRatio) {
        return circle(direction,radii.x,radii.y,heightRatio,0d,0d);
    }
    
    public static Circle circle(Facing facing, double radius, double innerRadius, double heightRatio) {
        return circle(VectorHelper.from(facing),radius,innerRadius,heightRatio,0d,0d);
    }
    
    public static Circle circle(Axis axis, double radius, double innerRadius, double heightRatio) {
        return circle(VectorHelper.from(axis),radius,innerRadius,heightRatio,0d,0d);
    }
    
    public static Circle circle(Vector3d direction, double radius, double innerRadius, double heightRatio) {
        return circle(direction,radius,innerRadius,heightRatio,0d,0d);
    }
    
    public static Circle circle(Facing facing, Vector2d radii, double heightRatio, Vector2d angles) {
        return circle(VectorHelper.from(facing),radii.x,radii.y,heightRatio,angles.x,angles.y);
    }
    
    public static Circle circle(Axis axis, Vector2d radii, double heightRatio, Vector2d angles) {
        return circle(VectorHelper.from(axis),radii.x,radii.y,heightRatio,angles.x,angles.y);
    }
    
    public static Circle circle(Vector3d direction, Vector2d radii, double heightRatio, Vector2d angles) {
        return circle(direction,radii.x,radii.y,heightRatio,angles.x,angles.y);
    }
    
    public static Circle circle(
            Facing facing, double radius, double innerRadius, double heightRatio, double startAngle, double endAngle) {
        return circle(VectorHelper.from(facing),radius,innerRadius,heightRatio,startAngle,endAngle);
    }
    
    public static Circle circle(
            Axis axis, double radius, double innerRadius, double heightRatio, double startAngle, double endAngle) {
        return circle(VectorHelper.from(axis),radius,innerRadius,heightRatio,startAngle,endAngle);
    }
    
    public static Circle circle(Vector3d direction, double radius, double innerRadius, double heightRatio,
            double startAngle, double endAngle) {
        return startAngle==endAngle || Math.abs(endAngle-startAngle)>=MathHelper.RADIANS_360 ?
                new Circle(direction,radius,innerRadius,heightRatio) :
                new CircleSlice(direction,radius,innerRadius,heightRatio,startAngle,endAngle);
    }
    
    public static Plane plane(
            Vector3d corner, Vector3d oppositeCorner, Vector2d relativeCorner, Vector2d relativeOppositeCorner) {
        return new Plane(oppositeCorner.sub(corner),relativeCorner,relativeOppositeCorner);
    }
    
    public static Plane plane(Vector3d corner, Vector3d oppositeCorner) {
        return new Plane(oppositeCorner.sub(corner),VectorHelper.negInf2D(),VectorHelper.inf2D());
    }
    
    public static Plane plane(Facing facing) {
        return new Plane(VectorHelper.from(facing),VectorHelper.negInf2D(),VectorHelper.inf2D());
    }
    
    public static Plane plane(Axis axis) {
        return new Plane(VectorHelper.from(axis),VectorHelper.negInf2D(),VectorHelper.inf2D());
    }
    
    public static Plane plane(Vector3d direction) {
        return new Plane(direction,VectorHelper.negInf2D(),VectorHelper.inf2D());
    }
    
    public static Plane plane(Facing facing, Vector2d corner, Vector2d oppositeCorner) {
        return new Plane(VectorHelper.from(facing),corner,oppositeCorner);
    }
    
    public static Plane plane(Axis axis, Vector2d corner, Vector2d oppositeCorner) {
        return new Plane(VectorHelper.from(axis),corner,oppositeCorner);
    }
    
    public static Plane plane(Vector3d direction, Vector2d corner, Vector2d oppositeCorner) {
        return new Plane(direction,corner,oppositeCorner);
    }
    
    public static Plane plane(Facing facing, double sideLength) {
        return plane(VectorHelper.from(facing),VectorHelper.zero2D(),sideLength,sideLength);
    }
    
    public static Plane plane(Axis axis, double sideLength) {
        return plane(VectorHelper.from(axis),VectorHelper.zero2D(),sideLength,sideLength);
    }
    
    public static Plane plane(Vector3d direction, double sideLength) {
        return plane(direction,VectorHelper.zero2D(),sideLength,sideLength);
    }
    
    public static Plane plane(Facing facing, double width, double height) {
        return plane(VectorHelper.from(facing),VectorHelper.zero2D(),width,height);
    }
    
    public static Plane plane(Axis axis, double width, double height) {
        return plane(VectorHelper.from(axis),VectorHelper.zero2D(),width,height);
    }
    
    public static Plane plane(Vector3d direction, double width, double height) {
        return plane(direction,VectorHelper.zero2D(),width,height);
    }
    
    public static Plane plane(Facing facing, Vector2d center, double width, double height) {
        return plane(VectorHelper.from(facing),center,width,height);
    }
    
    public static Plane plane(Axis axis, Vector2d center, double width, double height) {
        return plane(VectorHelper.from(axis),center,width,height);
    }
    
    public static Plane plane(Vector3d direction, Vector2d center, double width, double height) {
        return new Plane(direction,center.sub(width/2d,height/2d,new Vector2d()),
                         center.add(width/2d,height/2d,new Vector2d()));
    }
    
    public static Square square(Facing facing, double heightRatio) {
        return square(VectorHelper.from(facing),1d,heightRatio);
    }
    
    public static Square square(Axis axis, double heightRatio) {
        return square(VectorHelper.from(axis),1d,heightRatio);
    }
    
    public static Square square(Vector3d direction, double heightRatio) {
        return square(direction,1d,heightRatio);
    }
    
    public static Square square(Facing facing, double sideLength, double heightRatio) {
        return square(VectorHelper.from(facing),sideLength,heightRatio);
    }
    
    public static Square square(Axis axis, double sideLength, double heightRatio) {
        return square(VectorHelper.from(axis),sideLength,heightRatio);
    }
    
    public static Square square(Vector3d direction, double sideLength, double heightRatio) {
        return new Square(direction,sideLength,heightRatio);
    }
}