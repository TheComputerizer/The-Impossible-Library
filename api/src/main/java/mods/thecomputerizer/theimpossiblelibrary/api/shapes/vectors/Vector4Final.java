package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

public class Vector4Final extends Vector4 {
    
    public Vector4Final() {
        super();
    }
    
    public Vector4Final(Number x) {
        super(x);
    }
    
    public Vector4Final(Number x, Number y) {
        super(x,y);
    }
    
    public Vector4Final(Number x, Number y, Number z) {
        super(x,y,z);
    }
    
    public Vector4Final(Number x, Number y, Number z, Number w) {
        super(x,y,z,w);
    }
    
    @Override public void setW(Number w) {
        throw new UnsupportedOperationException("Cannot set w value for Vector4Final instance");
    }
    
    @Override public void setX(Number x) {
        throw new UnsupportedOperationException("Cannot set x value for Vector4Final instance");
    }
    
    @Override public void setY(Number y) {
        throw new UnsupportedOperationException("Cannot set y value for Vector4Final instance");
    }
    
    @Override public void setZ(Number z) {
        throw new UnsupportedOperationException("Cannot set z value for Vector4Final instance");
    }
}