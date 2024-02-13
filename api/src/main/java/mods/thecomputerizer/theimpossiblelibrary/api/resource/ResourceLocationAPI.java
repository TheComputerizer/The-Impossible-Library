package mods.thecomputerizer.theimpossiblelibrary.api.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;

public interface ResourceLocationAPI<R> {

    void bind(MinecraftAPI mc);
    /**
     * Returns the number of frames in a sprite sheet if a mcmeta file is detected
     */
    int getSpriteFrames();
    R get();
    String getNamespace();
    String getPath();
}