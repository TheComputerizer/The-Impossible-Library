package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

@SuppressWarnings("unused")
public interface VectorSuppliers<V> {
    
    V getNext();
    boolean hasNext();
    void onFinished();
    void reset();
    
    interface VectorSupplier2D extends VectorSuppliers<Vector2d> {}
    interface VectorSupplier3D extends VectorSuppliers<Vector3d> {}
    interface VectorSupplier4D extends VectorSuppliers<Vector4d> {}
}