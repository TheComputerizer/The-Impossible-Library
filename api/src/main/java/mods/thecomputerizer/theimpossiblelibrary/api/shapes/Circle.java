package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper.zero2D;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_180;
import static mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper.RADIANS_360;

@SuppressWarnings("unused") @Getter
public class Circle extends Shape2D {
    
    protected final int resolution;
    @Setter protected double radius;
    @Setter protected double innerRadius;
    @Setter protected double heightRatio;
    
    /**
     See ShapeHelper for alternative construction methods
     */
    public Circle(Vector3d direction, double radius, double innerRadius, double heightRatio) {
        this(direction,radius,innerRadius,heightRatio,360);
    }
    
    private Circle(Vector3d direction, double radius, double innerRadius, double heightRatio, int resolution) {
        super(direction);
        this.resolution = resolution;
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
        Vector2d[] vectors = new Vector2d[this.resolution+1];
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
    
    public double getStartAngle() {
        return 0d;
    }
    
    @Override public VectorSupplier2D getVectorSupplier() {
        return new CircleStream(this.resolution,0d,RADIANS_360/this.resolution,this.innerRadius,
                                this.radius,vec -> withRatio(vec.x,vec.y));
    }
    
    @Override public double getWidth() {
        return this.radius*2d;
    }
    
    @Override public boolean isInsideRelative(Vector2d pos) {
        double distance = zero2D().distance(pos.x/Math.min(this.heightRatio,1d),pos.y/Math.min(1d/this.heightRatio,1d));
        return distance>=this.innerRadius && distance<this.radius;
    }
    
    @Override public Vector2d random2D() {
        double radius = RandomHelper.randomDouble(this.innerRadius,this.radius);
        double angle = RandomHelper.randomDouble(RADIANS_360);
        return withRatio(Math.cos(angle)*radius,Math.sin(angle)*radius);
    }
    
    @Override public Vector3d random3D() {
        return new Vector3d(random2D(),0d);
    }
    
    public CircleSlice[] slice(int numSlices) {
        return slice(numSlices,0d);
    }
    
    public CircleSlice[] slice(int numSlices, double startAngle) {
        while(startAngle>RADIANS_180) startAngle-=RADIANS_360;
        numSlices = Math.max(numSlices,1);
        double sliceWidth = RADIANS_360/(double)numSlices;
        CircleSlice[] slices = new CircleSlice[numSlices];
        for(int i=0;i<numSlices;i++)
            slices[i] = new CircleSlice(new Vector3d(this.direction),this.radius,this.innerRadius,this.heightRatio,
                                        startAngle+(sliceWidth*i),startAngle+(sliceWidth*(i+1)));
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
            super(direction,radius,innerRadius,heightRatio,(int)((360d/(Math.max(startAngle,endAngle)-Math.min(startAngle,endAngle)))+1d));
            //if(startAngle>MathHelper.RADIANS_180) startAngle-=MathHelper.RADIANS_180;
            //if(endAngle>MathHelper.RADIANS_180) startAngle-=MathHelper.RADIANS_180;
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
            return new CircleStream(this.resolution,this.startAngle,
                                    (this.endAngle-this.startAngle)/this.resolution,
                                    this.innerRadius,this.radius,vec -> withRatio(vec.x,vec.y));
        }
        
        @Override public boolean isInsideRelative(Vector2d pos) {
            pos = new Vector2d(pos.x/Math.min(this.heightRatio,1d),pos.y/Math.min(1d/this.heightRatio,1d));
            double distance = zero2D().distance(pos);
            if(distance>=this.innerRadius && distance<this.radius) {
                double angle = Math.atan2(-pos.y,-pos.x)+MathHelper.RADIANS_180;
                return angle>=this.startAngle && angle<=this.endAngle;
            }
            return false;
        }
        
        @Override public Vector2d random2D() {
            double radius = RandomHelper.randomDouble(this.innerRadius,this.radius);
            double angle = RandomHelper.randomDouble(this.startAngle,this.endAngle);
            return withRatio(Math.cos(angle)*radius,Math.sin(angle)*radius);
        }
        
        @Override public CircleSlice[] slice(int numSlices) {
            numSlices = Math.max(numSlices,1);
            double sliceWidth = (this.endAngle-this.startAngle)/(double)numSlices;
            CircleSlice[] slices = new CircleSlice[numSlices];
            for(int i=0;i<this.resolution;i++)
                slices[i] = new CircleSlice(new Vector3d(this.direction),this.radius,this.innerRadius,this.heightRatio,
                        this.startAngle+(sliceWidth*i),this.startAngle+(sliceWidth*(i+1)));
            return slices;
        }
    }
    
    @Getter
    public static final class CircleStream implements VectorSupplier2D {
        
        private final int resolution;
        private final double startAngle;
        private final double angleDif;
        private final double innerRadius;
        private final double outerRadius;
        private final Function<Vector2d,Vector2d> pointScaling;
        private int resolutionCount;
        private int cornerCount;
        
        public CircleStream(int resolution, double startAngle, double angleDif, double innerRadius, double outerRadius,
                Function<Vector2d,Vector2d> pointScaling) {
            this.resolution = resolution;
            this.startAngle = startAngle;
            this.angleDif = angleDif;
            this.innerRadius = Math.min(innerRadius,outerRadius);
            this.outerRadius = Math.max(innerRadius,outerRadius);
            this.pointScaling = pointScaling;
        }
        
        @Override public Vector2d getNext() {
            double angle = this.startAngle+(this.angleDif*this.resolutionCount);
            if(this.cornerCount==1 || this.cornerCount==2) angle+=this.angleDif;
            double radius = this.cornerCount<=1 ? this.outerRadius : this.innerRadius;
            Vector2d next = new Vector2d(Math.cos(angle)*radius,Math.sin(angle)*radius);
            next = Objects.nonNull(this.pointScaling) ? this.pointScaling.apply(next) : next;
            this.cornerCount++;
            if(this.cornerCount>3) {
                this.cornerCount = 0;
                this.resolutionCount++;
            }
            return next;
        }
        
        @Override public boolean hasNext() {
            return this.resolutionCount<this.resolution;
        }
        
        @Override public void onFinished() {
        
        }
        
        @Override public void reset() {
            this.resolutionCount = 0;
            this.cornerCount = 0;
        }
    }
}