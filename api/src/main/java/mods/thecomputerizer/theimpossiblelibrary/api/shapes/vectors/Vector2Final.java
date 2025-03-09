package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

public class Vector2Final extends Vector2 {
    
    public Vector2Final() {
        super();
    }
    
    public Vector2Final(Number x) {
        super(x);
    }
    
    public Vector2Final(Number x, Number y) {
        super(x,y);
    }
    
    @Override public void setX(Number x) {
        throw new UnsupportedOperationException("Cannot set x value for Vector2Final instance");
    }
    
    @Override public void setY(Number y) {
        throw new UnsupportedOperationException("Cannot set y value for Vector2Final instance");
    }
}
