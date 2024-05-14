package mods.thecomputerizer.theimpossiblelibrary.api.resource;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;

import java.util.Objects;

@Getter
public abstract class ResourceLocationAPI<R> {
    
    protected final R instance;
    
    protected ResourceLocationAPI(R instance) {
        this.instance = instance;
    }

    public abstract void bind(MinecraftAPI mc);
    
    @Override
    public boolean equals(Object other) {
        return other instanceof ResourceLocationAPI<?> &&
               String.valueOf(this.instance).equals(String.valueOf(((ResourceLocationAPI<?>)other).getInstance()));
    }
    
    /**
     * Returns the number of frames in a sprite sheet if a mcmeta file is detected
     */
    public abstract int getSpriteFrames();
    public abstract String getNamespace();
    public abstract String getPath();
    
    @Override
    public String toString() {
        return Objects.nonNull(this.instance) ? this.instance.toString() : null;
    }

    /**
     * Specifically made for 1.12.2 integer dimension IDs so everything else doesn't have work around them
     */
    public static final class Pseudo<V> extends ResourceLocationAPI<V> {
        
        public Pseudo(V value) {
            super(value);
        }

        @Override
        public void bind(MinecraftAPI mc) {}

        @Override
        public int getSpriteFrames() {
            return 0;
        }

        @Override
        public String getNamespace() {
            return "";
        }

        @Override
        public String getPath() {
            return toString();
        }
    }
}