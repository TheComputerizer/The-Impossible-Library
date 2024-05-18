package mods.thecomputerizer.theimpossiblelibrary.api.shapes;

import org.joml.Vector3d;

public interface Shape {
    
    Shape2D[] getAs2DArray();
    double getDepth();
    double getHeight();
    double getWidth();
    boolean isInside(Vector3d pos);
}