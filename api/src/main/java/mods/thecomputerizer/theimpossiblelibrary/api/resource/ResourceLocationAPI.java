package mods.thecomputerizer.theimpossiblelibrary.api.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;

public abstract class ResourceLocationAPI<R> extends AbstractWrapped<R> {
    
    protected ResourceLocationAPI(R instance) {
        super(instance);
    }

    @IndirectCallers public abstract void bind(MinecraftAPI<?> mc);
    
    @Override public boolean equals(Object other) {
        return other instanceof ResourceLocationAPI<?> &&
               String.valueOf(this.wrapped).equals(String.valueOf(((ResourceLocationAPI<?>)other).getWrapped()));
    }
    
    /**
     * Returns the number of frames in a sprite sheet if a mcmeta file is detected
     */
    public abstract int getSpriteFrames();
    public abstract String getNamespace();
    public abstract String getPath();
    
    @Override public String toString() {
        return Objects.nonNull(this.wrapped) ? this.wrapped.toString() : null;
    }

    /**
     * Specifically made for 1.12.2 integer dimension IDs so everything else doesn't have work around them
     */
    public static final class Pseudo<V> extends ResourceLocationAPI<V> {
        
        public Pseudo(V value) {
            super(value);
        }

        @Override public void bind(MinecraftAPI<?> mc) {}

        @Override public int getSpriteFrames() {
            return 0;
        }

        @Override public String getNamespace() {
            return "";
        }

        @Override public String getPath() {
            return toString();
        }
    }
}