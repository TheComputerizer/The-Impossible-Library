package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2d;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.VectorHelper.zero2D;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_180;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_270;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_360;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_90;

@SuppressWarnings("unused") @Getter @Setter
public class Circle extends Shape2D {
    
    protected double radius;
    protected double innerRadius;
    
    public Circle(Vector3d direction, double radius) {
        this(direction,radius,0d);
    }
    
    public Circle(Vector3d direction, double radius, double innerRadius) {
        super(direction);
        if(innerRadius>radius) innerRadius = radius;
        if(innerRadius<0d) innerRadius = 0d;
        this.radius = radius;
        this.innerRadius = innerRadius;
    }
    
    @Override public Circle copy() {
        return getScaled(1d,1d);
    }
    
    public double[] getQuadCoords() {
        return new double[] {
                Math.cos(0d)*this.radius,Math.sin(0d)*this.radius,
                Math.cos(RADIANS_90)*this.radius,Math.sin(RADIANS_90)*this.radius,
                Math.cos(RADIANS_180)*this.radius,Math.sin(RADIANS_180)*this.radius,
                Math.cos(RADIANS_270)*this.radius,Math.sin(RADIANS_270)*this.radius
        };
    }
    
    @Override public Circle getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    @Override public Circle getScaled(Vector2d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Circle getScaled(double scale, double scaleInner) {
        if(scale<=0d) scale = 1d;
        if(scaleInner<=0d) scaleInner = 1d;
        return new Circle(new Vector3d(this.direction),this.radius*scale,this.innerRadius*scaleInner);
    }
    
    @Override public Circle getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Circle getScaled(double scaleX, double scaleY, double scaleZ) {
        return getScaled(scaleX,scaleY);
    }
    
    @Override public boolean isInsideRelative(Vector2d pos) {
        double distance = zero2D().distance(pos);
        return distance>=this.innerRadius && distance<=this.radius;
    }
    
    public CircleSlice[] slice(int resolution) {
        if(resolution<1) resolution = 1;
        double sliceWidth = RADIANS_360/resolution;
        CircleSlice[] slices = new CircleSlice[resolution];
        for(int i=0;i<resolution;i++)
            slices[i] = new CircleSlice(this.direction,this.radius,this.innerRadius,sliceWidth*i,sliceWidth*(i+1));
        return slices;
    }
    
    @Override public double getDepth() {
        return 0d;
    }
    
    @Override public double getHeight() {
        return this.radius*2d;
    }
    
    @Override public double getWidth() {
        return this.radius*2d;
    }
    
    public static class CircleSlice extends Circle {
        
        /**
         Must be in radians
         */
        protected final double startAngle;
        protected final double endAngle;
        
        public CircleSlice(Vector3d direction, double radius, double innerRadius,double startAngle, double endAngle) {
            super(direction,radius,innerRadius);
            this.startAngle = startAngle;
            this.endAngle = endAngle;
        }
        
        @Override public CircleSlice copy() {
            return getScaled(1d,1d,1d);
        }
        
        @Override
        public double[] getQuadCoords() {
            return new double[] {
                    Math.cos(this.startAngle)*this.innerRadius,Math.sin(0d)*Math.sin(this.startAngle)*this.innerRadius,
                    Math.cos(this.endAngle)*this.innerRadius,Math.sin(this.endAngle)*this.innerRadius,
                    Math.cos(this.endAngle)*this.radius,Math.sin(this.endAngle)*this.radius,
                    Math.cos(this.startAngle)*this.radius,Math.sin(0d)*Math.sin(this.startAngle)*this.radius
            };
        }
        
        @Override public CircleSlice getScaled(double scale) {
            return getScaled(scale,scale,1d);
        }
        
        @Override public CircleSlice getScaled(Vector2d scale) {
            return getScaled(scale.x,scale.y,1d);
        }
        
        @Override public CircleSlice getScaled(double scale, double scaleInner) {
            return getScaled(scale,scaleInner,1d);
        }
        
        @Override public CircleSlice getScaled(Vector3d scale) {
            return getScaled(scale.x,scale.y,scale.z);
        }
        
        @Override public CircleSlice getScaled(double scale, double scaleInner, double scaleAngle) {
            if(scale<=0d) scale = 1d;
            if(scaleInner<=0d) scaleInner = 1d;
            if(scaleAngle<=0d) scaleAngle = 1d;
            double radius = this.radius*scale;
            double innerRadius = this.innerRadius*scaleInner;
            double endAngle = this.startAngle+((this.endAngle-this.startAngle)*scaleAngle);
            return new CircleSlice(new Vector3d(this.direction),radius,innerRadius,this.startAngle,endAngle);
        }
        
        @Override public boolean isInsideRelative(Vector2d pos) {
            if(super.isInsideRelative(pos)) {
                double angle = pos.angle(new Vector2d(0d,1d));
                return angle>=this.startAngle && angle<=this.endAngle;
            }
            return false;
        }
    }
}