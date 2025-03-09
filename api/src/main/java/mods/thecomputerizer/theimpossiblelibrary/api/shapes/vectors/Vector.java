package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

/**
 *
 */
public interface Vector {
    
    Vector add(Vector v);
    Vector addScalar(Number n);
    double angle(Vector v);
    Number[] array();
    byte[] bArrray();
    
    default byte bW() {
        return w().byteValue();
    }
    
    default byte bX() {
        return x().byteValue();
    }
    
    default byte bY() {
        return y().byteValue();
    }
    
    default byte bZ() {
        return z().byteValue();
    }
    Vector copy();
    int count();
    Vector cross(Vector v);
    double[] dArrray();
    double distance(Vector v);
    Vector div(Vector v);
    Vector divScalar(Number n);
    double dot(Vector v);
    
    default double dW() {
        return w().doubleValue();
    }
    
    default double dX() {
        return x().doubleValue();
    }
    
    default double dY() {
        return y().doubleValue();
    }
    
    default double dZ() {
        return z().doubleValue();
    }
    
    float[] fArrray();
    
    default float fW() {
        return w().floatValue();
    }
    
    default float fX() {
        return x().floatValue();
    }
    
    default float fY() {
        return y().floatValue();
    }
    
    default float fZ() {
        return z().floatValue();
    }
    
    int[] iArrray();
    
    default int iW() {
        return w().intValue();
    }
    
    default int iX() {
        return x().intValue();
    }
    
    default int iY() {
        return y().intValue();
    }
    
    default int iZ() {
        return z().intValue();
    }
    
    long[] lArrray();
    
    default long lW() {
        return w().longValue();
    }
    
    default long lX() {
        return x().longValue();
    }
    
    default long lY() {
        return y().longValue();
    }
    
    default long lZ() {
        return z().longValue();
    }
    
    Vector mul(Vector v);
    Vector mulScalar(Number n);
    short[] sArrray();
    void setW(Number w);
    void setX(Number x);
    void setY(Number y);
    void setZ(Number z);
    Vector sub(Vector v);
    Vector subScalar(Number n);
    
    default short sW() {
        return w().shortValue();
    }
    
    default short sX() {
        return x().shortValue();
    }
    
    default short sY() {
        return y().shortValue();
    }
    
    default short sZ() {
        return z().shortValue();
    }
    
    Vector vW();
    Vector vX();
    Vector vXW();
    Vector vXY();
    Vector vXYZ();
    Vector vXZ();
    Vector vXZW();
    Vector vY();
    Vector vYW();
    Vector vYZ();
    Vector vYZW();
    Vector vZ();
    Vector vZW();
    Number w();
    Number x();
    Number y();
    Number z();
}