package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

@Setter
public class Vector4 implements Vector {
    
    protected Number x;
    protected Number y;
    protected Number z;
    protected Number w;
    
    public Vector4() {
        this(0,0,0,0);
    }
    
    public Vector4(Number x) {
        this(x,0,0,0);
    }
    
    public Vector4(Number x, Number y) {
        this(x,y,0,0);
    }
    
    public Vector4(Number x, Number y, Number z) {
        this(x,y,z,0);
    }
    
    public Vector4(Number x, Number y, Number z, Number w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    @Override public Vector4 add(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberAdd(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberAdd(this.y,v.y()));
                if(count>2) {
                    setZ(GenericUtils.numberAdd(this.z,v.z()));
                    if(count>3) setW(GenericUtils.numberAdd(this.w,v.w()));
                }
            }
        }
        return this;
    }
    
    @Override public Vector4 addScalar(Number n) {
        setX(GenericUtils.numberAdd(this.x,n));
        setY(GenericUtils.numberAdd(this.y,n));
        setZ(GenericUtils.numberAdd(this.z,n));
        setW(GenericUtils.numberAdd(this.w,n));
        return this;
    }
    
    @Override public double angle(Vector v) {
        if(v.count()<4) return 0d;
        double dX = dX();
        double dY = dY();
        double dZ = dZ();
        double dW = dW();
        double vdX = v.dX();
        double vdY = v.dY();
        double vdZ = v.dZ();
        double vdW = v.dW();
        double l1 = dX*dX+(dY*dY+(dZ*dZ+(dW*dW)));
        double l2 = vdX*vdX+(vdY*vdY+(vdZ*vdZ+(vdW*vdW)));
        double l3 = dX*vdX+(dY*vdY+(dZ*vdZ+(dW*vdW)));
        double angle = l3/Math.sqrt(l1*l2);
        return Math.acos(angle<1 ? (angle>-1 ? angle : -1) : 1);
    }
    
    @Override public Number[] array() {
        return new Number[]{this.x,this.y,this.z,this.w};
    }
    
    @Override public byte[] bArrray() {
        return new byte[]{bX(),bY(),bZ(),bW()};
    }
    
    @Override public Vector4 copy() {
        return new Vector4(this.x,this.y,this.z,this.w);
    }
    
    @Override public int count() {
        return 4;
    }
    
    @Override public Vector cross(Vector v) {
        return this;
    }
    
    @Override public double[] dArrray() {
        return new double[]{dX(),dY(),dZ(),dW()};
    }
    
    @Override public double distance(Vector v) {
        if(v.count()<4) return 0d;
        double dx = dX()-v.dX();
        double dy = dY()-v.dY();
        double dz = dZ()-v.dZ();
        double dw = dW()-v.dW();
        return Math.sqrt((dx*dx)+(dy*dy)+(dz*dz)+(dw*dw));
    }
    
    @Override public Vector4 div(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberDiv(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberDiv(this.y,v.y()));
                if(count>2) {
                    setZ(GenericUtils.numberDiv(this.z,v.z()));
                    if(count>3) setW(GenericUtils.numberDiv(this.w,v.w()));
                }
            }
        }
        return this;
    }
    
    @Override public Vector4 divScalar(Number n) {
        setX(GenericUtils.numberDiv(this.x,n));
        setY(GenericUtils.numberDiv(this.y,n));
        setZ(GenericUtils.numberDiv(this.z,n));
        setW(GenericUtils.numberDiv(this.w,n));
        return this;
    }
    
    @Override public double dot(Vector v) {
        int count = v.count();
        if(count==0) return 0d;
        double total = dX()*v.dX();
        if(count>1) total+=(dY()*v.dY());
        if(count>2) total+=(dZ()*v.dZ());
        if(count>3) total+=(dW()*v.dW());
        return total;
    }
    
    @Override public float[] fArrray() {
        return new float[]{fX(),fY(),fZ(),fW()};
    }
    
    @Override public int[] iArrray() {
        return new int[]{iX(),iY(),iZ(),iW()};
    }
    
    @Override public long[] lArrray() {
        return new long[]{lX(),lY(),lZ(),lW()};
    }
    
    @Override public Vector4 mul(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberMul(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberMul(this.y,v.y()));
                if(count>2) {
                    setZ(GenericUtils.numberMul(this.z,v.z()));
                    if(count>3) setW(GenericUtils.numberMul(this.w,v.w()));
                }
            }
        }
        return this;
    }
    
    @Override public Vector4 mulScalar(Number n) {
        setX(GenericUtils.numberMul(this.x,n));
        setY(GenericUtils.numberMul(this.y,n));
        setZ(GenericUtils.numberMul(this.z,n));
        setW(GenericUtils.numberMul(this.w,n));
        return this;
    }
    
    @Override public short[] sArrray() {
        return new short[]{sX(),sY(),sZ(),sW()};
    }
    
    @Override public Vector4 sub(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberMul(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberMul(this.y,v.y()));
                if(count>2) {
                    setZ(GenericUtils.numberMul(this.z,v.z()));
                    if(count>3) setW(GenericUtils.numberMul(this.w,v.w()));
                }
            }
        }
        return this;
    }
    
    @Override public Vector4 subScalar(Number n) {
        setX(GenericUtils.numberSub(this.x,n));
        setY(GenericUtils.numberSub(this.y,n));
        setZ(GenericUtils.numberSub(this.z,n));
        setW(GenericUtils.numberSub(this.w,n));
        return this;
    }
    
    @Override public Vector vW() {
        return new Vector1(w());
    }
    
    @Override public Vector vX() {
        return new Vector1(x());
    }
    
    @Override public Vector vXW() {
        return new Vector2(x(),w());
    }
    
    @Override public Vector vXY() {
        return new Vector2(x(),y());
    }
    
    @Override public Vector vXYZ() {
        return new Vector3(x(),y(),z());
    }
    
    @Override public Vector vXZ() {
        return new Vector2(x(),z());
    }
    
    @Override public Vector vXZW() {
        return new Vector3(x(),z(),w());
    }
    
    @Override public Vector vY() {
        return new Vector1(y());
    }
    
    @Override public Vector vYW() {
        return new Vector2(y(),w());
    }
    
    @Override public Vector vYZ() {
        return new Vector2(y(),z());
    }
    
    @Override public Vector vYZW() {
        return new Vector3(y(),z(),w());
    }
    
    @Override public Vector vZ() {
        return new Vector1(z());
    }
    
    @Override public Vector vZW() {
        return new Vector2(z(),w());
    }
    
    @Override public Number w() {
        return this.w;
    }
    
    @Override public Number x() {
        return this.x;
    }
    
    @Override public Number y() {
        return this.y;
    }
    
    @Override public Number z() {
        return this.z;
    }
}