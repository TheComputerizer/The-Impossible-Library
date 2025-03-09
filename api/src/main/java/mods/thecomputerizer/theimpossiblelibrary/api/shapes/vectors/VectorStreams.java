package mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier4D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class VectorStreams<V> {
    
    public static VectorStream2D get2D(Iterable<Vector2> vectors) {
        return get2D(ArrayHelper.fromIterable(vectors,Vector2.class));
    }
    
    public static VectorStream2D get2D(Vector2 ... vectors) {
        return new VectorStream2D(vectors);
    }
    
    public static VectorStream3D get3D(Iterable<Vector3> vectors) {
        return get3D(ArrayHelper.fromIterable(vectors,Vector3.class));
    }
    
    public static VectorStream3D get3D(Vector3 ... vectors) {
        return new VectorStream3D(vectors);
    }
    
    public static VectorStream4D get4D(Iterable<Vector4> vectors) {
        return get4D(ArrayHelper.fromIterable(vectors,Vector4.class));
    }
    
    public static VectorStream4D get4D(Vector4 ... vectors) {
        return new VectorStream4D(vectors);
    }
    
    protected final V[] vectors;
    @Getter protected int index;
    
    @SafeVarargs protected VectorStreams(V ... vectors) {
        this.vectors = Objects.nonNull(vectors) ? vectors : getDefault();
        this.index = 0;
    }
    
    @Override public boolean equals(Object other) {
        if(this==other) return true;
        if(Objects.isNull(other)) return false;
        if(other instanceof VectorSuppliers) {
            VectorSuppliers<?> vectors = (VectorSuppliers<?>)other;
            int savedIndex = getIndex();
            int savedIndexOther = vectors.getIndex();
            reset();
            vectors.reset();
            boolean equals = true;
            while(hasNext()) {
                V next = getNext();
                if(!vectors.hasNext() || !Misc.equalsNullable(next,vectors.getNext())) {
                    equals = false;
                    break;
                }
            }
            if(this instanceof VectorSuppliers<?>) ((VectorSuppliers<?>)this).seekTo(savedIndex);
            vectors.seekTo(savedIndexOther);
            return equals;
        }
        return false;
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
    
    public static class VectorStream2D extends VectorStreams<Vector2> implements VectorSupplier2D {
        
        protected VectorStream2D(Vector2 ... vectors) {
            super(vectors);
            for(int i=0;i<vectors.length;i++)
                if(Objects.isNull(vectors[i])) vectors[i] = new Vector2();
        }
        
        @Override protected Vector2[] getDefault() {
            return new Vector2[]{};
        }
    }
    
    public static class VectorStream3D extends VectorStreams<Vector3> implements VectorSupplier3D {
        
        protected VectorStream3D(Vector3 ... vectors) {
            super(vectors);
            for(int i=0;i<vectors.length;i++)
                if(Objects.isNull(vectors[i])) vectors[i] = new Vector3();
        }
        
        @Override protected Vector3[] getDefault() {
            return new Vector3[]{};
        }
    }
    
    public static class VectorStream4D extends VectorStreams<Vector4> implements VectorSupplier4D {
        
        protected VectorStream4D(Vector4... vectors) {
            super(vectors);
            for(int i=0;i<vectors.length;i++)
                if(Objects.isNull(vectors[i])) vectors[i] = new Vector4();
        }
        
        @Override protected Vector4[] getDefault() {
            return new Vector4[]{};
        }
    }
}