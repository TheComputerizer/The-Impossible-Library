package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

@SuppressWarnings("unused")
public interface VectorSuppliers<V> {
    
    default int count() {
        int size = 0;
        while(hasNext()) {
            size++;
            getNext();
        }
        reset();
        return size;
    }
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
    
    interface VectorSupplier2D extends VectorSuppliers<Vector2> {}
    interface VectorSupplier3D extends VectorSuppliers<Vector3> {}
    interface VectorSupplier4D extends VectorSuppliers<Vector4> {}
}