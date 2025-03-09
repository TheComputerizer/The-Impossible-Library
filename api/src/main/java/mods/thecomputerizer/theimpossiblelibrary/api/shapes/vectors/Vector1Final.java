package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

public class Vector1Final extends Vector1 {
    
    public Vector1Final() {
        super();
    }
    
    public Vector1Final(Number x) {
        super(x);
    }
    
    @Override public void setX(Number x) {
        throw new UnsupportedOperationException("Cannot set x value for Vector1Final instance");
    }
}
