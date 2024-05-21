package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper.zero2D;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_180;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_270;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_360;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_90;

@SuppressWarnings("unused") @Getter
public class Circle extends Shape2D {
    
    protected int resolution = 100;
    @Setter protected double radius;
    @Setter protected double innerRadius;
    @Setter protected double heightRatio;
    
    /**
     See ShapeHelper for alternative construction methods
     */
    public Circle(Vector3d direction, double radius, double innerRadius, double heightRatio) {
        super(direction);
        if(innerRadius<0d) innerRadius = 0d;
        if(radius<0d) radius = 0d;
        this.radius = Math.max(innerRadius,radius);
        this.innerRadius = Math.min(innerRadius,radius);
        this.heightRatio = heightRatio;
    }
    
    @Override public Circle copy() {
        return getScaled(1d,1d);
    }
    
    public Vector3d getCenter(Vector3d center) {
        Vector2d center2D = getCenter();
        return new Vector3d(center.x+center2D.x,center.y+center2D.y,center.z);
    }
    
    public Vector2d getCenter() {
        return new Vector2d(0d,0d);
    }
    
    @Override public double getDepth() {
        return 0d;
    }
    
    @Override public double getHeight() {
        return this.radius*2d;
    }
    
    @Override public VectorSupplier2D getOutlineSupplier() {
        double sliceWidth = RADIANS_360/(double)(this.resolution);
        Vector2d[] vectors = new Vector2d[this.resolution];
        for(int i=0;i<vectors.length;i++)
            vectors[i] = withRatio(Math.cos(sliceWidth*i)*this.radius,Math.sin(sliceWidth*i)*this.radius);
        return VectorStreams.get2D(vectors);
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
        return new Circle(new Vector3d(this.direction),this.radius*scale,this.innerRadius*scaleInner,
                          this.heightRatio);
    }
    
    @Override public Circle getScaled(Vector3d scale) {
        return getScaled(scale.x,scale.y);
    }
    
    @Override public Circle getScaled(double scaleX, double scaleY, double scaleZ) {
        return getScaled(scaleX,scaleY);
    }
    
    @Override public VectorSupplier2D getVectorSupplier() {
        return VectorStreams.get2D(
                withRatio(Math.cos(0d)*this.radius,Math.sin(0d)*this.radius),
                withRatio(Math.cos(RADIANS_90)*this.radius,Math.sin(RADIANS_90)*this.radius),
                withRatio(Math.cos(RADIANS_180)*this.radius,Math.sin(RADIANS_180)*this.radius),
                withRatio(Math.cos(RADIANS_270)*this.radius,Math.sin(RADIANS_270)*this.radius)
        );
    }
    
    @Override public double getWidth() {
        return this.radius*2d;
    }
    
    @Override public boolean isInsideRelative(Vector2d pos) {
        double distance = zero2D().distance(pos);
        return distance>=this.innerRadius && distance<=this.radius;
    }
    
    @Override public Vector2d random2D() {
        double radius = RandomHelper.randomDouble(this.innerRadius,this.radius);
        double angle = RandomHelper.randomDouble(RADIANS_360);
        return withRatio(Math.cos(angle)*radius,Math.sin(angle)*radius);
    }
    
    @Override public Vector3d random3D() {
        return new Vector3d(random2D(),0d);
    }
    
    public void setResolution(int resolution) {
        this.resolution = Math.max(resolution,4);
    }
    
    public CircleSlice[] slice() {
        double sliceWidth = RADIANS_360/(double)this.resolution;
        CircleSlice[] slices = new CircleSlice[this.resolution];
        for(int i=0;i<this.resolution;i++)
            slices[i] = new CircleSlice(new Vector3d(this.direction),this.radius,this.innerRadius,this.heightRatio,
                                        sliceWidth*i,sliceWidth*(i+1));
        return slices;
    }
    
    protected Vector2d withRatio(double x, double y) {
        return new Vector2d(x*Math.min(this.heightRatio,1d),y*Math.min(1d/this.heightRatio,1d));
    }
    
    public static class CircleSlice extends Circle {
        
        /**
         Must be in radians
         */
        protected final double startAngle;
        protected final double endAngle;
        
        /**
         See ShapeHelper for alternative construction methods
         */
        public CircleSlice(Vector3d direction, double radius, double innerRadius, double heightRatio,
                double startAngle, double endAngle) {
            super(direction,radius,innerRadius,heightRatio);
            this.startAngle = Math.min(startAngle,endAngle);
            this.endAngle = Math.max(startAngle,endAngle);
        }
        
        @Override public CircleSlice copy() {
            return getScaled(1d,1d,1d);
        }
        
        @Override public Vector2d getCenter() {
            double radius = MathHelper.getHalfway(this.innerRadius,this.radius);
            double angle = MathHelper.getHalfway(this.startAngle,this.endAngle);
            return withRatio(Math.cos(angle)*radius,Math.sin(angle)*radius);
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
            return new CircleSlice(new Vector3d(this.direction),radius,innerRadius,this.heightRatio,this.startAngle,endAngle);
        }
        
        @Override public VectorSupplier2D getVectorSupplier() {
            return VectorStreams.get2D(
                    withRatio(Math.cos(this.startAngle)*this.radius,Math.sin(this.startAngle)*this.radius),
                    withRatio(Math.cos(this.endAngle)*this.radius,Math.sin(this.endAngle)*this.radius),
                    withRatio(Math.cos(this.endAngle)*this.innerRadius,Math.sin(this.endAngle)*this.innerRadius),
                    withRatio(Math.cos(this.startAngle)*this.innerRadius,Math.sin(this.startAngle)*this.innerRadius)
            );
        }
        
        @Override public boolean isInsideRelative(Vector2d pos) {
            if(super.isInsideRelative(pos)) {
                double angle = pos.angle(new Vector2d(0d,1d));
                return angle>=this.startAngle && angle<=this.endAngle;
            }
            return false;
        }
        
        @Override public Vector2d random2D() {
            double radius = RandomHelper.randomDouble(this.innerRadius,this.radius);
            double angle = RandomHelper.randomDouble(this.startAngle,this.endAngle);
            return withRatio(Math.cos(angle)*radius,Math.sin(angle)*radius);
        }
    }
}