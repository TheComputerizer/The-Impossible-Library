package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import static java.lang.Double.NaN;

/**
 * It's just a scalar tho
 */
public class Vector1 implements Vector {
    
    public static Vector1 get() {
        return get(0);
    }
    
    public static Vector1 get(Number x) {
        return new Vector1(x);
    }
    
    protected Number x;
    
    public Vector1() {
        this(0);
    }
    
    public Vector1(Number x) {
        this.x = x;
    }
    
    @Override public Vector1 add(Vector v) {
        if(v.count()==0) return this;
        setX(GenericUtils.numberAdd(this.x,v.x()));
        return this;
    }
    
    @Override public Vector1 addScalar(Number n) {
        setX(GenericUtils.numberAdd(this.x,n));
        return this;
    }
    
    @Override public double angle(Vector v) {
        return 0d;
    }
    
    @Override public Number[] array() {
        return new Number[]{this.x};
    }
    
    @Override public byte[] bArrray() {
        return new byte[]{bX()};
    }
    
    @Override public Vector1 copy() {
        return new Vector1(this.x);
    }
    
    @Override public int count() {
        return 1;
    }
    
    @Override public Vector cross(Vector v) {
        throw new UnsupportedOperationException("Cannot perform cross product operation on Vector1");
    }
    
    @Override public double[] dArrray() {
        return new double[]{dX()};
    }
    
    @Override public double distance(Vector v) {
        return v.count()==0 ? 0d : Math.abs(dX()-v.dX());
    }
    
    @Override public Vector1 div(Vector v) {
        if(v.count()==0) return this;
        setX(GenericUtils.numberDiv(this.x,v.x()));
        return this;
    }
    
    @Override public Vector1 divScalar(Number n) {
        setX(GenericUtils.numberDiv(this.x,n));
        return this;
    }
    
    @Override public double dot(Vector v) {
        return v.count()==0 ? 0d : dX()*v.dX();
    }
    
    @Override public float[] fArrray() {
        return new float[]{fX()};
    }
    
    @Override public int[] iArrray() {
        return new int[]{iX()};
    }
    
    @Override public long[] lArrray() {
        return new long[]{lX()};
    }
    
    @Override public Vector1 mul(Vector v) {
        if(v.count()==0) return this;
        setX(GenericUtils.numberMul(this.x,v.x()));
        return this;
    }
    
    @Override public Vector1 mulScalar(Number n) {
        setX(GenericUtils.numberMul(this.x,n));
        return this;
    }
    
    @Override public short[] sArrray() {
        return new short[]{sX()};
    }
    
    @Override public void setW(Number w) {
        throw new UnsupportedOperationException("Cannot set w value for Vector1");
    }
    
    @Override public void setX(Number x) {
        this.x = x;
    }
    
    @Override public void setY(Number y) {
        throw new UnsupportedOperationException("Cannot set y value for Vector1");
    }
    
    @Override public void setZ(Number z) {
        throw new UnsupportedOperationException("Cannot set z value for Vector1");
    }
    
    @Override public Vector1 sub(Vector v) {
        if(v.count()==0) return this;
        setX(GenericUtils.numberSub(this.x,v.x()));
        return this;
    }
    
    @Override public Vector1 subScalar(Number n) {
        setX(GenericUtils.numberSub(this.x,n));
        return this;
    }
    
    @Override public Vector vW() {
        throw new UnsupportedOperationException("Cannot get Vector1 W from Vector1");
    }
    
    @Override public Vector vX() {
        return this;
    }
    
    @Override public Vector vXW() {
        throw new UnsupportedOperationException("Cannot get Vector2 XW from Vector1");
    }
    
    @Override public Vector vXY() {
        throw new UnsupportedOperationException("Cannot get Vector2 XY from Vector1");
    }
    
    @Override public Vector vXYZ() {
        throw new UnsupportedOperationException("Cannot get Vector3 XYZ from Vector1");
    }
    
    @Override public Vector vXZ() {
        throw new UnsupportedOperationException("Cannot get Vector2 XZ from Vector1");
    }
    
    @Override public Vector vXZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 XZW from Vector1");
    }
    
    @Override public Vector vY() {
        throw new UnsupportedOperationException("Cannot get Vector1 Y from Vector1");
    }
    
    @Override public Vector vYW() {
        throw new UnsupportedOperationException("Cannot get Vector2 YW from Vector1");
    }
    
    @Override public Vector vYZ() {
        throw new UnsupportedOperationException("Cannot get Vector2 YZ from Vector1");
    }
    
    @Override public Vector vYZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 YZW from Vector1");
    }
    
    @Override public Vector vZ() {
        throw new UnsupportedOperationException("Cannot get Vector1 Z from Vector1");
    }
    
    @Override public Vector vZW() {
        throw new UnsupportedOperationException("Cannot get Vector2 ZW from Vector1");
    }
    
    @Override public Number w() {
        return NaN;
    }
    
    @Override public Number x() {
        return this.x;
    }
    
    @Override public Number y() {
        return NaN;
    }
    
    @Override public Number z() {
        return NaN;
    }
}