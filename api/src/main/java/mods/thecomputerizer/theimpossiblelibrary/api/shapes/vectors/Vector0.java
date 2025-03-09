package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import static java.lang.Double.NaN;

/**
 * Represents an empty vector. This is basically just a vector-specific alternative for null.
 */
public final class Vector0 implements Vector {
    
    public static final Vector0 INSTANCE = new Vector0();
    
    private Vector0() {}
    
    @Override public Vector add(Vector v) {
        return this;
    }
    
    @Override public Vector addScalar(Number n) {
        return this;
    }
    
    @Override public double angle(Vector v) {
        return 0d;
    }
    
    @Override public Number[] array() {
        return new Number[]{};
    }
    
    @Override public byte[] bArrray() {
        return new byte[]{};
    }
    
    @Override public Vector0 copy() {
        return this; //No reason to actually make a copy
    }
    
    @Override public int count() {
        return 0;
    }
    
    @Override public Vector cross(Vector v) {
        return this;
    }
    
    @Override public double[] dArrray() {
        return new double[]{};
    }
    
    @Override public double distance(Vector v) {
        return 0d;
    }
    
    @Override public Vector div(Vector v) {
        return this;
    }
    
    @Override public Vector divScalar(Number n) {
        return this;
    }
    
    @Override public double dot(Vector v) {
        return NaN;
    }
    
    @Override public float[] fArrray() {
        return new float[]{};
    }
    
    @Override public int[] iArrray() {
        return new int[]{};
    }
    
    @Override public long[] lArrray() {
        return new long[]{};
    }
    
    @Override public Vector mul(Vector v) {
        return this;
    }
    
    @Override public Vector mulScalar(Number n) {
        return this;
    }
    
    @Override public short[] sArrray() {
        return new short[]{};
    }
    
    @Override public void setW(Number w) {
        throw new UnsupportedOperationException("Cannot set w value for Vector0");
    }
    
    @Override public void setX(Number x) {
        throw new UnsupportedOperationException("Cannot set x value for Vector0");
    }
    
    @Override public void setY(Number y) {
        throw new UnsupportedOperationException("Cannot set y value for Vector0");
    }
    
    @Override public void setZ(Number z) {
        throw new UnsupportedOperationException("Cannot set z value for Vector0");
    }
    
    @Override public Vector sub(Vector v) {
        return this;
    }
    
    @Override public Vector subScalar(Number n) {
        return this;
    }
    
    @Override public Vector vW() {
        throw new UnsupportedOperationException("Cannot get Vector1 W from Vector0");
    }
    
    @Override public Vector vX() {
        throw new UnsupportedOperationException("Cannot get Vector1 X from Vector0");
    }
    
    @Override public Vector vXW() {
        throw new UnsupportedOperationException("Cannot get Vector2 XW from Vector0");
    }
    
    @Override public Vector vXY() {
        throw new UnsupportedOperationException("Cannot get Vector2 XY from Vector0");
    }
    
    @Override public Vector vXYZ() {
        throw new UnsupportedOperationException("Cannot get Vector3 XYZ from Vector0");
    }
    
    @Override public Vector vXZ() {
        throw new UnsupportedOperationException("Cannot get Vector2 XZ from Vector0");
    }
    
    @Override public Vector vXZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 XZW from Vector0");
    }
    
    @Override public Vector vY() {
        throw new UnsupportedOperationException("Cannot get Vector1 Y from Vector0");
    }
    
    @Override public Vector vYW() {
        throw new UnsupportedOperationException("Cannot get Vector2 YW from Vector0");
    }
    
    @Override public Vector vYZ() {
        throw new UnsupportedOperationException("Cannot get Vector2 YZ from Vector0");
    }
    
    @Override public Vector vYZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 YZW from Vector0");
    }
    
    @Override public Vector vZ() {
        throw new UnsupportedOperationException("Cannot get Vector1 Z from Vector0");
    }
    
    @Override public Vector vZW() {
        throw new UnsupportedOperationException("Cannot get Vector2 ZW from Vector0");
    }
    
    @Override public Number w() {
        return NaN;
    }
    
    @Override public Number x() {
        return NaN;
    }
    
    @Override public Number y() {
        return NaN;
    }
    
    @Override public Number z() {
        return NaN;
    }
}