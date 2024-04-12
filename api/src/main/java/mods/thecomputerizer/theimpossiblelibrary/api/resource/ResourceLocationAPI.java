package mods.thecomputerizer.theimpossiblelibrary.api.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;

import java.util.Objects;

public interface ResourceLocationAPI<R> {

    void bind(MinecraftAPI mc);
    /**
     * Returns the number of frames in a sprite sheet if a mcmeta file is detected
     */
    int getSpriteFrames();
    R get();
    String getNamespace();
    String getPath();

    /**
     * Specifically made for 1.12.2 integer dimension IDs so everything else doesn't have work around them
     */
    final class Psuedo<V> implements ResourceLocationAPI<V> {

        private final V value;

        public Psuedo(V value) {
            this.value = value;
        }

        @Override
        public void bind(MinecraftAPI mc) {}

        @Override
        public int getSpriteFrames() {
            return 0;
        }

        @Override
        public V get() {
            return this.value;
        }

        @Override
        public String getNamespace() {
            return "";
        }

        @Override
        public String getPath() {
            return toString();
        }

        @Override
        public String toString() {
            return Objects.nonNull(this.value) ? this.value.toString() : null;
        }
    }
}