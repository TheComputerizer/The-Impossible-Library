package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;

import static java.lang.Double.NaN;

@Setter
public class Vector3 implements Vector {
    
    protected Number x;
    protected Number y;
    protected Number z;
    
    public Vector3() {
        this(0,0,0);
    }
    
    public Vector3(Number x) {
        this(x,0,0);
    }
    
    public Vector3(Vector2 v) {
        this(v.x,v.y,0);
    }
    
    public Vector3(Number x, Number y) {
        this(x,y,0);
    }
    
    public Vector3(Vector2 v, Number z) {
        this(v.x,v.y,z);
    }
    
    public Vector3(Vector3 copy) {
        this(copy.x,copy.y,copy.z);
    }
    
    public Vector3(Number x, Number y, Number z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3 add(Number x, Number y, Number z) {
        return add(x,y,z,this);
    }
    
    public Vector3 add(Vector3 v, Vector3 dst) {
        return add(v.x,v.y,v.z,dst);
    }
    
    public Vector3 add(Number x, Number y, Number z, Vector3 dst) {
        dst.setX(GenericUtils.numberAdd(this.x,x));
        dst.setY(GenericUtils.numberAdd(this.y,y));
        dst.setZ(GenericUtils.numberAdd(this.z,z));
        return dst;
    }
    
    @Override public Vector3 add(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberAdd(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberAdd(this.y,v.y()));
                if(count>2) setZ(GenericUtils.numberAdd(this.z,v.z()));
            }
        }
        return this;
    }
    
    @Override public Vector3 addScalar(Number n) {
        setX(GenericUtils.numberAdd(this.x,n));
        setY(GenericUtils.numberAdd(this.y,n));
        setZ(GenericUtils.numberAdd(this.z,n));
        return this;
    }
    
    @Override public double angle(Vector v) {
        if(v.count()<3) return 0d;
        double dX = dX();
        double dY = dY();
        double dZ = dZ();
        double vdX = v.dX();
        double vdY = v.dY();
        double vdZ = v.dZ();
        double l1 = dX*dX+(dY*dY+(dZ*dZ));
        double l2 = vdX*vdX+(vdY*vdY+(vdZ*vdZ));
        double l3 = dX*vdX+(dY*vdY+(dZ*vdZ));
        double angle = l3/Math.sqrt(l1*l2);
        return Math.acos(angle<1 ? (angle>-1 ? angle : -1) : 1);
    }
    
    @Override public Number[] array() {
        return new Number[]{this.x,this.y,this.z};
    }
    
    @Override public byte[] bArrray() {
        return new byte[]{bX(),bY(),bZ()};
    }
    
    @Override public Vector3 copy() {
        return new Vector3(this.x,this.y,this.z);
    }
    
    @Override public int count() {
        return 3;
    }
    
    @Override public Vector3 cross(Vector v) {
        return cross(v,this);
    }
    
    public Vector3 cross(Vector v, Vector3 dst) {
        if(v.count()!=3) return dst;
        double dX = dX();
        double dY = dY();
        double dZ = dZ();
        double vdX = v.dX();
        double vdY = v.dY();
        double vdZ = v.dZ();
        dst.setX(dY*vdZ+(-dZ*vdY));
        dst.setY(dZ*vdX+(-dX*vdZ));
        dst.setZ(dX*vdY+(-dY*vdX));
        return dst;
    }
    
    @Override public double[] dArrray() {
        return new double[]{dX(),dY(),dZ()};
    }
    
    @Override public double distance(Vector v) {
        if(v.count()<3) return 0d;
        double dx = dX()-v.dX();
        double dy = dY()-v.dY();
        double dz = dZ()-v.dZ();
        return Math.sqrt(dx*dx+(dy*dy)+(dz*dz));
    }
    
    @Override public Vector3 div(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberDiv(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberDiv(this.y,v.y()));
                if(count>2) setZ(GenericUtils.numberDiv(this.z,v.z()));
            }
        }
        return this;
    }
    
    @Override public Vector3 divScalar(Number n) {
        setX(GenericUtils.numberDiv(this.x,n));
        setY(GenericUtils.numberDiv(this.y,n));
        setZ(GenericUtils.numberDiv(this.z,n));
        return this;
    }
    
    @Override public double dot(Vector v) {
        int count = v.count();
        if(count==0) return 0d;
        double total = dX()*v.dX();
        if(count>1) total+=(dY()*v.dY());
        if(count>2) total+=(dZ()*v.dZ());
        return total;
    }
    
    @Override public float[] fArrray() {
        return new float[]{fX(),fY(),fZ()};
    }
    
    @Override public int[] iArrray() {
        return new int[]{iX(),iY(),iZ()};
    }
    
    @Override public long[] lArrray() {
        return new long[]{lX(),lY(),lZ()};
    }
    
    public Vector3 max(Vector3 v) {
        return max(v,this);
    }
    
    public Vector3 max(Vector3 v, Vector3 dst) {
        dst.x = dX()>v.dX() ? x : v.x();
        dst.y = dY()>v.dY() ? y : v.y();
        dst.z = dZ()>v.dZ() ? z : v.z();
        return dst;
    }
    
    public Vector3 min(Vector3 v) {
        return min(v,this);
    }
    
    public Vector3 min(Vector3 v, Vector3 dst) {
        dst.x = dX()<v.dX() ? x : v.x();
        dst.y = dY()<v.dY() ? y : v.y();
        dst.z = dZ()<v.dZ() ? z : v.z();
        return dst;
    }
    
    @Override public Vector3 mul(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberMul(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberMul(this.y,v.y()));
                if(count>2) setZ(GenericUtils.numberMul(this.z,v.z()));
            }
        }
        return this;
    }
    
    @Override public Vector3 mulScalar(Number n) {
        setX(GenericUtils.numberMul(this.x,n));
        setY(GenericUtils.numberMul(this.y,n));
        setZ(GenericUtils.numberMul(this.z,n));
        return this;
    }
    
    public Vector3 normalize() {
        return normalize(this);
    }
    
    public Vector3 normalize(Vector3 dst) {
        double dX = dX();
        double dY = dY();
        double dZ = dZ();
        double iLen = 1d/Math.sqrt((dX*dX)+(dY*dY)+(dZ*dZ));
        dst.setX(dX*iLen);
        dst.setY(dY*iLen);
        dst.setX(dZ*iLen);
        return dst;
    }
    
    public Vector3 rotateX(double angle) {
        return rotateX(angle,this);
    }
    
    public Vector3 rotateX(double angle, Vector3 dst) {
        double sin = Math.sin(angle);
        double cos = MathHelper.cosFromSin(angle, sin);
        double y = dX()*cos-dZ()*sin;
        double z = dY()*sin+dZ()*cos;
        dst.setX(this.x);
        dst.setY(y);
        dst.setZ(z);
        return dst;
    }
    
    public Vector3 rotateY(double angle) {
        return rotateY(angle,this);
    }
    
    public Vector3 rotateY(double angle, Vector3 dst) {
        double sin = Math.sin(angle);
        double cos = MathHelper.cosFromSin(angle,sin);
        double x = dX()*cos-dZ()*sin;
        double z = -dX()*sin+dZ()*cos;
        dst.setX(x);
        dst.setY(this.y);
        dst.setZ(z);
        return dst;
    }
    
    public Vector3 rotateZ(double angle) {
        return rotateZ(angle,this);
    }
    
    public Vector3 rotateZ(double angle, Vector3 dst) {
        double sin = Math.sin(angle);
        double cos = MathHelper.cosFromSin(angle,sin);
        double x = dX()*cos-dY()*sin;
        double y = dX()*sin+dY()*cos;
        dst.setX(x);
        dst.setY(y);
        dst.setZ(this.z);
        return dst;
    }
    
    @Override public short[] sArrray() {
        return new short[]{sX(),sY(),sZ()};
    }
    
    @Override public void setW(Number w) {
        throw new UnsupportedOperationException("Cannot set w value for Vector3");
    }
    
    public Vector3 sub(Number x, Number y, Number z) {
        return sub(x,y,z,this);
    }
    
    public Vector3 sub(Number x, Number y, Number z, Vector3 dst) {
        dst.setX(GenericUtils.numberSub(this.x,x));
        dst.setY(GenericUtils.numberSub(this.y,y));
        dst.setZ(GenericUtils.numberSub(this.z,z));
        return dst;
    }
    
    @Override public Vector3 sub(Vector v) {
        int count = v.count();
        if(count>0) {
            setX(GenericUtils.numberSub(this.x,v.x()));
            if(count>1) {
                setY(GenericUtils.numberSub(this.y,v.y()));
                if(count>2) setZ(GenericUtils.numberSub(this.z,v.z()));
            }
        }
        return this;
    }
    
    @Override public Vector3 subScalar(Number n) {
        setX(GenericUtils.numberSub(this.x,n));
        setY(GenericUtils.numberSub(this.y,n));
        setZ(GenericUtils.numberSub(this.z,n));
        return this;
    }
    
    @Override public Vector vW() {
        throw new UnsupportedOperationException("Cannot get Vector1 W from Vector3");
    }
    
    @Override public Vector vX() {
        return new Vector1(x());
    }
    
    @Override public Vector vXW() {
        throw new UnsupportedOperationException("Cannot get Vector2 XW from Vector3");
    }
    
    @Override public Vector vXY() {
        return new Vector2(x(),y());
    }
    
    @Override public Vector vXYZ() {
        return this;
    }
    
    @Override public Vector vXZ() {
        return new Vector2(x(),z());
    }
    
    @Override public Vector vXZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 XZW from Vector3");
    }
    
    @Override public Vector vY() {
        return new Vector1(y());
    }
    
    @Override public Vector vYW() {
        throw new UnsupportedOperationException("Cannot get Vector2 YW from Vector3");
    }
    
    @Override public Vector vYZ() {
        return new Vector2(y(),z());
    }
    
    @Override public Vector vYZW() {
        throw new UnsupportedOperationException("Cannot get Vector3 YZW from Vector3");
    }
    
    @Override public Vector vZ() {
        return new Vector1(z());
    }
    
    @Override public Vector vZW() {
        throw new UnsupportedOperationException("Cannot get Vector2 ZW from Vector3");
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
        return this.z;
    }
}