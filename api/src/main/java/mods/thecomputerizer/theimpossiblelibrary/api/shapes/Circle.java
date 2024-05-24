package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.function.Function;

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
    
    @Override public boolean checkToleranceBounds(Vector3d center, Box bounds) {
        return bounds.expand(this.radius).isInside(getCenter(center));
    }
    
    @Override public Circle copy() {
        return getScaled(1d,1d);
    }
    
    @Override public boolean equals(Object other) {
        if(this==other) return true;
        if(Objects.isNull(other)) return false;
        if(other.getClass()==Circle.class) {
            Circle circle = (Circle)other;
            return sameDirection(circle) && this.resolution==circle.resolution &&
                   this.innerRadius==circle.innerRadius && this.radius==circle.radius &&
                   this.heightRatio==circle.heightRatio;
        }
        return false;
    }
    
    public double getAngleDif() {
        return Math.abs(getAngleEnd()-getAngleStart());
    }
    
    public double getAngleStart() {
        return -RADIANS_180;
    }
    
    public double getAngleEnd() {
        return RADIANS_180;
    }
    
    @Override public double getBoundedX(double x, double y, double z) {
        return getBoundedXY(x,y).x;
    }
    
    @Override public Vector2d getBoundedXY(Vector2d xy) {
        return getBoundedXY(xy.x,xy.y);
    }
    
    @Override public Vector2d getBoundedXY(double x, double y) {
        Vector2d polar = VectorHelper.toPolar(x,y);
        double radius = Math.max(this.innerRadius,Math.min(this.radius,polar.x));
        double start = getAngleStart();
        double end = getAngleEnd();
        if(start>end) {
            double d = end;
            end = start;
            start = d;
        }
        double angle = Math.max(start,Math.min(end,polar.y));
        return VectorHelper.toCartesian(radius,angle);
    }
    
    @Override public double getBoundedY(double x, double y, double z) {
        return getBoundedXY(x,y).y;
    }
    
    @Override public Vector3d getCenter(Vector3d center) {
        Vector2d center2D = getCenter();
        return new Vector3d(center.x+center2D.x,center.y+center2D.y,center.z);
    }
    
    public Vector2d getCenter() {
        double radius = MathHelper.getHalfway(this.innerRadius,this.radius);
        double angle = MathHelper.getBoundedAngle(MathHelper.getHalfway(getAngleStart(),getAngleEnd()));
        return withRatio(VectorHelper.toCartesian(radius,angle));
    }
    
    @Override public double getDepth() {
        return 0d;
    }
    
    @Override public double getHeight() {
        return this.radius*2d;
    }
    
    @Override public VectorSupplier2D getOutlineSupplier(Box bounds) {
        double sliceWidth = getAngleDif()/(double)(this.resolution);
        double start = getAngleStart();
        Vector2d[] vectors = new Vector2d[this.resolution+1];
        for(int i=0;i<vectors.length;i++)
            vectors[i] = bounds.getBoundedXY(withRatio(VectorHelper.toCartesian(this.radius,start+(sliceWidth*i))));
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
    
    @Override public VectorSupplier2D getVectorSupplier(Box bounds) {
        return new CircleStream(this,getAngleStart(),
                getAngleDif()/this.resolution,vec -> bounds.getBoundedXY(withRatio(VectorHelper.toCartesian(vec))));
    }
    
    @Override public double getWidth() {
        return this.radius*2d;
    }
    
    @Override public boolean isInsideRelative(Vector2d pos) {
        pos = VectorHelper.toPolar(pos.x/Math.min(this.heightRatio,1d),pos.y/Math.min(1d/this.heightRatio,1d));
        if(pos.x>=this.innerRadius && pos.x<this.radius) {
            while(pos.y<0) pos.y+=RADIANS_360;
            double start = getAngleStart();
            while(start<0d) start+=RADIANS_360;
            double end = getAngleEnd();
            while(end<start) end+=RADIANS_360;
            return pos.y>=start && pos.y<end;
        }
        return false;
    }
    
    @Override public Vector2d random2D() {
        double radius = RandomHelper.randomDouble(this.innerRadius,this.radius);
        double start = getAngleStart();
        double angle = RandomHelper.randomDouble(start,start+getAngleDif());
        return withRatio(VectorHelper.toCartesian(radius,angle));
    }
    
    @Override public Vector3d random3D() {
        return new Vector3d(random2D(),0d);
    }
    
    public CircleSlice[] slice(int numSlices) {
        return slice(numSlices,0d);
    }
    
    public CircleSlice[] slice(int numSlices, double angleOffset) {
        numSlices = Math.max(numSlices,1);
        double start = getAngleStart();
        double sliceWidth = getAngleDif()/(double)numSlices;
        CircleSlice[] slices = new CircleSlice[numSlices];
        for(int i=0;i<numSlices;i++)
            slices[i] = new CircleSlice(new Vector3d(this.direction),this.radius,this.innerRadius,this.heightRatio,
                                        start+angleOffset+(sliceWidth*i),start+angleOffset+(sliceWidth*(i+1)));
        return slices;
    }
    
    protected Vector2d withRatio(Vector2d v) {
        return withRatio(v.x,v.y);
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
            super(direction,radius,innerRadius,heightRatio,(int)((360d*(Math.abs(endAngle-startAngle)/RADIANS_360)+1d)));
            this.startAngle = startAngle;
            this.endAngle = endAngle;
        }
        
        @Override public boolean checkToleranceBounds(Vector3d center, Box bounds) {
            return bounds.expand((this.radius-this.innerRadius)/2d).isInside(getCenter(center));
        }
        
        @Override public CircleSlice copy() {
            return getScaled(1d,1d,1d);
        }
        
        @Override public boolean equals(Object other) {
            if(this==other) return true;
            if(Objects.isNull(other)) return false;
            if(other.getClass()==CircleSlice.class) {
                CircleSlice slice = (CircleSlice)other;
                return sameDirection(slice) && this.resolution==slice.resolution &&
                       this.innerRadius==slice.innerRadius && this.radius==slice.radius &&
                       this.heightRatio==slice.heightRatio && this.startAngle==slice.startAngle &&
                       this.endAngle==slice.endAngle;
            }
            return false;
        }
        
        @Override public double getAngleStart() {
            return this.startAngle;
        }
        
        @Override public double getAngleEnd() {
            return this.endAngle;
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
            double start = getAngleStart();
            return new CircleSlice(new Vector3d(this.direction),radius,innerRadius,this.heightRatio,start,
                                   start+(getAngleDif()*scaleAngle));
        }
    }
    
    @Getter
    public static final class CircleStream implements VectorSupplier2D {
        
        private final Circle circle;
        private final double startAngle;
        private final double angleDif;
        private final Function<Vector2d,Vector2d> vertexSupplier;
        private int resolutionCount;
        private int cornerCount;
        
        public CircleStream(Circle circle, double startAngle, double angleDif,
                Function<Vector2d,Vector2d> vertexSupplier) {
            this.circle = circle;
            this.startAngle = startAngle;
            this.angleDif = angleDif;
            this.vertexSupplier = vertexSupplier;
        }
        
        @Override public boolean equals(Object other) {
            if(this==other) return true;
            if(Objects.isNull(other)) return false;
            if(other.getClass()==CircleStream.class) {
                CircleStream stream = (CircleStream)other;
                return Misc.equalsNullable(this.circle,stream.circle) && this.startAngle==stream.startAngle &&
                       this.angleDif==stream.angleDif;
            }
            return false;
        }
        
        @Override public int getIndex() {
            return (this.resolutionCount*4)+this.cornerCount;
        }
        
        @Override public Vector2d getNext() {
            double angle = this.startAngle+(this.angleDif*this.resolutionCount);
            if(this.cornerCount==1 || this.cornerCount==2) angle+=this.angleDif;
            double radius = this.cornerCount<=1 ? this.circle.radius : this.circle.innerRadius;
            Vector2d next = this.vertexSupplier.apply(new Vector2d(radius,angle));
            this.cornerCount++;
            if(this.cornerCount>3) {
                this.cornerCount = 0;
                this.resolutionCount++;
            }
            return next;
        }
        
        @Override public boolean hasNext() {
            return this.resolutionCount<this.circle.resolution;
        }
        
        @Override public void onFinished() {
        
        }
        
        @Override public void reset() {
            this.resolutionCount = 0;
            this.cornerCount = 0;
        }
    }
}