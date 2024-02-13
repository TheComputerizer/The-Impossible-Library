package mods.thecomputerizer.theimpossiblelibrary.api.client.resource;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;

public interface ResourceLocationAPI {

    void bind(MinecraftAPI mc);
    /**
     * Returns the number of frames in a sprite sheet if a mcmeta file is detected
     */
    int getSpriteFrames();
    String getNamespace();
    String getPath();
}