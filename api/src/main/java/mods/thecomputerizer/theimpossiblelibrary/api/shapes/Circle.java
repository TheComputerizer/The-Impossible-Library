package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

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
    public Circle(Vector3 direction, double radius, double innerRadius, double heightRatio) {
        this(direction,radius,innerRadius,heightRatio,360);
    }
    
    private Circle(Vector3 direction, double radius, double innerRadius, double heightRatio, int resolution) {
        super(direction);
        this.resolution = resolution;
        if(innerRadius<0d) innerRadius = 0d;
        if(radius<0d) radius = 0d;
        this.radius = Math.max(innerRadius,radius);
        this.innerRadius = Math.min(innerRadius,radius);
        this.heightRatio = heightRatio;
    }
    
    @Override public boolean checkToleranceBounds(Vector3 center, Box bounds) {
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
    
    public double getAngleClamped(double angle) {
        while(angle<0) angle+=RADIANS_360;
        while(angle>=RADIANS_360) angle-=RADIANS_360;
        return angle;
    }
    
    public double getAngleDif() {
        return Math.abs(getAngleEnd()-getAngleStart());
    }
    
    public double getAngleEnd() {
        return RADIANS_180;
    }
    
    public double getAngleStart() {
        return -RADIANS_180;
    }
    
    @Override public double getBoundedX(double x, double y, double z) {
        return getBoundedXY(x,y).dX();
    }
    
    @Override public Vector2 getBoundedXY(Vector2 xy) {
        return getBoundedXY(xy.dX(),xy.dY());
    }
    
    @Override public Vector2 getBoundedXY(double x, double y) {
        Vector2 polar = VectorHelper.toPolar(x,y);
        double radius = Math.max(this.innerRadius,Math.min(this.radius,polar.dX()));
        double start = getAngleStart();
        double end = getAngleEnd();
        if(start>end) {
            double d = end;
            end = start;
            start = d;
        }
        double angle = Math.max(start,Math.min(end,polar.dY()));
        return VectorHelper.toCartesian(radius,angle);
    }
    
    @Override public double getBoundedY(double x, double y, double z) {
        return getBoundedXY(x,y).dY();
    }
    
    @Override public Vector3 getCenter(Vector3 center) {
        Vector2 center2D = getCenter();
        return new Vector3(center.dX()+center2D.dX(),center.dY()+center2D.dY(),center.dZ());
    }
    
    public Vector2 getCenter() {
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
        Vector2[] vectors = new Vector2[this.resolution+1];
        for(int i=0;i<vectors.length;i++)
            vectors[i] = bounds.getBoundedXY(withRatio(VectorHelper.toCartesian(this.radius,start+(sliceWidth*i))));
        return VectorStreams.get2D(vectors);
    }
    
    @Override public Circle getScaled(double scale) {
        return getScaled(scale,scale);
    }
    
    @Override public Circle getScaled(Vector2 scale) {
        return getScaled(scale.dX(),scale.dY());
    }
    
    @Override public Circle getScaled(double scale, double scaleInner) {
        if(scale<=0d) scale = 1d;
        if(scaleInner<=0d) scaleInner = 1d;
        return new Circle(new Vector3(this.direction),this.radius*scale,this.innerRadius*scaleInner,
                          this.heightRatio);
    }
    
    @Override public Circle getScaled(Vector3 scale) {
        return getScaled(scale.dX(),scale.dY());
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
    
    @Override public boolean isInsideRelative(Vector2 pos) {
        Vector2 polar = VectorHelper.toPolar(pos.dX()/Math.min(this.heightRatio,1d),pos.dY()/Math.min(1d/this.heightRatio,1d));
        double radius = polar.dX();
        double dif = getAngleDif();
        if(radius>=this.innerRadius && radius<this.radius && dif>0d) {
            if(dif>=RADIANS_360) return true;
            double angle = getAngleClamped(polar.dY());
            double start = getAngleClamped(getAngleStart());
            double end = getAngleClamped(getAngleEnd());
            return start<end ? (angle>=start && angle<end) : (angle>=start || angle<end);
        }
        return false;
    }
    
    @Override public Vector2 random2D() {
        double radius = RandomHelper.randomDouble(this.innerRadius,this.radius);
        double start = getAngleStart();
        double angle = RandomHelper.randomDouble(start,start+getAngleDif());
        return withRatio(VectorHelper.toCartesian(radius,angle));
    }
    
    @Override public Vector3 random3D() {
        return new Vector3(random2D(),0d);
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
            slices[i] = new CircleSlice(new Vector3(this.direction),this.radius,this.innerRadius,this.heightRatio,
                                        start+angleOffset+(sliceWidth*i),start+angleOffset+(sliceWidth*(i+1)));
        return slices;
    }
    
    protected Vector2 withRatio(Vector2 v) {
        return withRatio(v.dX(),v.dY());
    }
    
    protected Vector2 withRatio(double x, double y) {
        return new Vector2(x*Math.min(this.heightRatio,1d),y*Math.min(1d/this.heightRatio,1d));
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
        public CircleSlice(Vector3 direction, double radius, double innerRadius, double heightRatio,
                double startAngle, double endAngle) {
            super(direction,radius,innerRadius,heightRatio,(int)((360d*(Math.abs(endAngle-startAngle)/RADIANS_360)+1d)));
            this.startAngle = startAngle;
            this.endAngle = endAngle;
        }
        
        @Override public boolean checkToleranceBounds(Vector3 center, Box bounds) {
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
        
        @Override public CircleSlice getScaled(Vector2 scale) {
            return getScaled(scale.dX(),scale.dY(),1d);
        }
        
        @Override public CircleSlice getScaled(double scale, double scaleInner) {
            return getScaled(scale,scaleInner,1d);
        }
        
        @Override public CircleSlice getScaled(Vector3 scale) {
            return getScaled(scale.dX(),scale.dY(),scale.dZ());
        }
        
        @Override public CircleSlice getScaled(double scale, double scaleInner, double scaleAngle) {
            if(scale<=0d) scale = 1d;
            if(scaleInner<=0d) scaleInner = 1d;
            if(scaleAngle<=0d) scaleAngle = 1d;
            double radius = this.radius*scale;
            double innerRadius = this.innerRadius*scaleInner;
            double start = getAngleStart();
            return new CircleSlice(new Vector3(this.direction),radius,innerRadius,this.heightRatio,start,
                                   start+(getAngleDif()*scaleAngle));
        }
    }
    
    @Getter
    public static final class CircleStream implements VectorSupplier2D {
        
        private final Circle circle;
        private final double startAngle;
        private final double angleDif;
        private final Function<Vector2,Vector2> vertexSupplier;
        private int resolutionCount;
        private int cornerCount;
        
        public CircleStream(Circle circle, double startAngle, double angleDif,
                Function<Vector2,Vector2> vertexSupplier) {
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
        
        @Override public Vector2 getNext() {
            double angle = this.startAngle+(this.angleDif*this.resolutionCount);
            if(this.cornerCount==1 || this.cornerCount==2) angle+=this.angleDif;
            double radius = this.cornerCount<=1 ? this.circle.radius : this.circle.innerRadius;
            Vector2 next = this.vertexSupplier.apply(new Vector2(radius,angle));
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