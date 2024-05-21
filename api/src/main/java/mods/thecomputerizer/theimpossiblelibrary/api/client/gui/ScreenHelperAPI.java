package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;

public interface ScreenHelperAPI {

    TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled);
    void open(ScreenAPI screen);
    void playVanillaClickSound();
}