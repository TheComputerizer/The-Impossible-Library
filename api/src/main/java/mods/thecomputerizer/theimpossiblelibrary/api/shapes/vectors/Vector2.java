package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import static java.lang.Double.NaN;

public class Vector2 implements Vector {
    
    public static Vector2 get() {
        return get(0,0);
    }
    
    public static Vector2 get(Number x, Number y) {
        return new Vector2(x,y);
    }
    
    public static Vector2 getX(Number x) {
        return get(x,0);
    }
    
    public static Vector2 getY(Number y) {
        return get(0,y);
    }
    
    public static Vector2 same(Number n) {
        return new Vector2(n,n);
    }
    
    protected Number x;
    protected Number y;
    
    public Vector2() {
        this(0,0);
    }
    
    public Vector2(Number x) {
        this(x,0);
    }
    
    public Vector2(Number x, Number y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector2 add(Number x, Number y) {
        return add(x,y,this);
    }
    
    public Vector2 add(Number x, Number y, Vector2 dst) {
        dst.setX(GenericUtils.numberAdd(this.x,x));
        dst.setY(GenericUtils.numberAdd(this.y,y));
        return dst;
    }
    
    @Override public Vector2 add(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberAdd(this.x,v.x()));
            if(count>1) setY(GenericUtils.numberAdd(this.y,v.y()));
        }
        return this;
    }
    
    @Override public Vector2 addScalar(Number n) {
        setX(GenericUtils.numberAdd(this.x,n));
        setY(GenericUtils.numberAdd(this.y,n));
        return this;
    }
    
    @Override public double angle(Vector v) {
        if(v.count()<2) return 0d;
        double dX = dX();
        double dY = dY();
        double vdX = v.dX();
        double vdY = v.dY();
        return Math.atan2((dX*vdY)-(dY*vdX),(dX*vdX)+(dY*vdY));
    }
    
    @Override public Number[] array() {
        return new Number[]{this.x,this.y};
    }
    
    @Override public byte[] bArrray() {
        return new byte[]{bX(),bY()};
    }
    
    @Override public Vector2 copy() {
        return new Vector2(this.x,this.y);
    }
    
    @Override public int count() {
        return 2;
    }
    
    @Override public Vector cross(Vector v) {
        throw new UnsupportedOperationException("Cannot perform cross product operation on Vector2");
    }
    
    @Override public double[] dArrray() {
        return new double[]{dX(),dY()};
    }
    
    @Override public double distance(Vector v) {
        if(v.count()<2) return 0d;
        double dx = dX()-v.dX();
        double dy = dY()-v.dY();
        return Math.sqrt(dx*dx+(dy*dy));
    }
    
    @Override public Vector2 div(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberDiv(this.x,v.x()));
            if(count>1) setY(GenericUtils.numberDiv(this.y,v.y()));
        }
        return this;
    }
    
    @Override public Vector2 divScalar(Number n) {
        setX(GenericUtils.numberDiv(this.x,n));
        setY(GenericUtils.numberDiv(this.y,n));
        return this;
    }
    
    @Override public double dot(Vector v) {
        int count = v.count();
        if(count==0) return 0d;
        double total = dX()*v.dX();
        if(count>1) total+=(dY()*v.dY());
        return total;
    }
    
    @Override public float[] fArrray() {
        return new float[]{fX(),fY()};
    }
    
    @Override public int[] iArrray() {
        return new int[]{iX(),iY()};
    }
    
    @Override public long[] lArrray() {
        return new long[]{lX(),lY()};
    }
    
    @Override public Vector2 mul(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberMul(this.x,v.x()));
            if(count>1) setY(GenericUtils.numberMul(this.y,v.y()));
        }
        return this;
    }
    
    public Vector2 mulScalar(Number n, Vector2 dst) {
        dst.setX(GenericUtils.numberMul(this.x,n));
        dst.setY(GenericUtils.numberMul(this.y,n));
        return dst;
    }
    
    @Override public Vector2 mulScalar(Number n) {
        setX(GenericUtils.numberMul(this.x,n));
        setY(GenericUtils.numberMul(this.y,n));
        return this;
    }
    
    @Override public short[] sArrray() {
        return new short[]{sX(),sY()};
    }
    
    @Override public void setW(Number w) {
        throw new UnsupportedOperationException("Cannot set w value for Vector2");
    }
    
    @Override public void setX(Number x) {
        this.x = x;
    }
    
    @Override public void setY(Number y) {
        this.y = y;
    }
    
    @Override public void setZ(Number z) {
        throw new UnsupportedOperationException("Cannot set z value for Vector2");
    }
    
    public Vector2 sub(Number x, Number y) {
        return sub(x,y,this);
    }
    
    public Vector2 sub(Number x, Number y, Vector2 dst) {
        dst.setX(GenericUtils.numberSub(this.x,x));
        dst.setY(GenericUtils.numberSub(this.y,y));
        return dst;
    }
    
    @Override public Vector2 sub(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberSub(this.x,v.x()));
            if(count>1) setY(GenericUtils.numberSub(this.y,v.y()));
        }
        return this;
    }
    
    @Override public Vector2 subScalar(Number n) {
        setX(GenericUtils.numberSub(this.x,n));
        setY(GenericUtils.numberSub(this.y,n));
        return this;
    }
    
    @Override public Vector vW() {
        throw new UnsupportedOperationException("Cannot get Vector1 W from Vector2");
    }
    
    @Override public Vector vX() {
        return new Vector1(x());
    }
    
    @Override public Vector vXW() {
        throw new UnsupportedOperationException("Cannot get Vector2 XW from Vector2");
    }
    
    @Override public Vector vXY() {
        return this;
    }
    
    @Override public Vector vXYZ() {
        throw new UnsupportedOperationException("Cannot get Vector3 XYZ from Vector2");
    }
    
    @Override public Vector vXZ() {
        throw new UnsupportedOperationException("Cannot get Vector2 XZ from Vector2");
    }
    
    @Override public Vector vXZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 XZW from Vector2");
    }
    
    @Override public Vector vY() {
        return new Vector1(y());
    }
    
    @Override public Vector vYW() {
        throw new UnsupportedOperationException("Cannot get Vector2 YW from Vector2");
    }
    
    @Override public Vector vYZ() {
        throw new UnsupportedOperationException("Cannot get Vector2 YZ from Vector2");
    }
    
    @Override public Vector vYZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 YZW from Vector2");
    }
    
    @Override public Vector vZ() {
        throw new UnsupportedOperationException("Cannot get Vector1 Z from Vector2");
    }
    
    @Override public Vector vZW() {
        throw new UnsupportedOperationException("Cannot get Vector2 ZW from Vector2");
    }
    
    @Override public Number w() {
        return NaN;
    }
    
    @Override public Number x() {
        return this.x;
    }
    
    @Override public Number y() {
        return this.y;
    }
    
    @Override public Number z() {
        return NaN;
    }
}