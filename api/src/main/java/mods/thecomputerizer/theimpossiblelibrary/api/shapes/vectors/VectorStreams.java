package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier4D;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class VectorStreams<V> {
    
    public static VectorStream2D get2D(Iterable<Vector2d> vectors) {
        return get2D(ArrayHelper.fromIterable(vectors,Vector2d.class));
    }
    
    public static VectorStream2D get2D(Vector2d ... vectors) {
        return new VectorStream2D(vectors);
    }
    
    public static VectorStream3D get3D(Iterable<Vector3d> vectors) {
        return get3D(ArrayHelper.fromIterable(vectors,Vector3d.class));
    }
    
    public static VectorStream3D get3D(Vector3d ... vectors) {
        return new VectorStream3D(vectors);
    }
    
    public static VectorStream4D get4D(Iterable<Vector4d> vectors) {
        return get4D(ArrayHelper.fromIterable(vectors,Vector4d.class));
    }
    
    public static VectorStream4D get4D(Vector4d ... vectors) {
        return new VectorStream4D(vectors);
    }
    
    private final V[] vectors;
    private int index;
    
    @SafeVarargs protected VectorStreams(V ... vectors) {
        this.vectors = Objects.nonNull(vectors) ? vectors : getDefault();
        this.index = 0;
    }
    
    protected abstract V[] getDefault();
    
    public V getNext() {
        V next = this.vectors[this.index];
        this.index++;
        return next;
    }
    
    public boolean hasNext() {
        return this.index<this.vectors.length;
    }
    
    public void onFinished() {}
    
    public void reset() {
        this.index = 0;
    }
    
    public static class VectorStream2D extends VectorStreams<Vector2d> implements VectorSupplier2D {
        
        protected VectorStream2D(Vector2d ... vectors) {
            super(vectors);
            for(int i=0;i<vectors.length;i++)
                if(Objects.isNull(vectors[i])) vectors[i] = new Vector2d();
        }
        
        @Override protected Vector2d[] getDefault() {
            return new Vector2d[]{};
        }
    }
    
    public static class VectorStream3D extends VectorStreams<Vector3d> implements VectorSupplier3D {
        
        protected VectorStream3D(Vector3d ... vectors) {
            super(vectors);
            for(int i=0;i<vectors.length;i++)
                if(Objects.isNull(vectors[i])) vectors[i] = new Vector3d();
        }
        
        @Override protected Vector3d[] getDefault() {
            return new Vector3d[]{};
        }
    }
    
    public static class VectorStream4D extends VectorStreams<Vector4d> implements VectorSupplier4D {
        
        protected VectorStream4D(Vector4d... vectors) {
            super(vectors);
            for(int i=0;i<vectors.length;i++)
                if(Objects.isNull(vectors[i])) vectors[i] = new Vector4d();
        }
        
        @Override protected Vector4d[] getDefault() {
            return new Vector4d[]{};
        }
    }
}