package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

public class Vector3Final extends Vector3 {
    
    public Vector3Final() {
        super();
    }
    
    public Vector3Final(Number x) {
        super(x);
    }
    
    public Vector3Final(Number x, Number y) {
        super(x,y);
    }
    
    public Vector3Final(Number x, Number y, Number z) {
        super(x,y,z);
    }
    
    @Override public void setX(Number x) {
        throw new UnsupportedOperationException("Cannot set x value for Vector3Final instance");
    }
    
    @Override public void setY(Number y) {
        throw new UnsupportedOperationException("Cannot set y value for Vector3Final instance");
    }
    
    @Override public void setZ(Number z) {
        throw new UnsupportedOperationException("Cannot set z value for Vector3Final instance");
    }
}