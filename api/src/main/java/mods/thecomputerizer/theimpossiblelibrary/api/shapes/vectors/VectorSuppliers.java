package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

@SuppressWarnings("unused")
public interface VectorSuppliers<V> {
    
    int getIndex();
    V getNext();
    boolean hasNext();
    void onFinished();
    void reset();
    default void seekTo(int index) {
        int i = getIndex();
        if(index<i) {
            reset();
            i = getIndex();
        }
        while(i<index && hasNext()) {
            getNext();
            i = getIndex();
        }
    }
    
    interface VectorSupplier2D extends VectorSuppliers<Vector2d> {}
    interface VectorSupplier3D extends VectorSuppliers<Vector3d> {}
    interface VectorSupplier4D extends VectorSuppliers<Vector4d> {}
}