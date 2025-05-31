package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import org.jetbrains.annotations.Nullable;


public interface ScreenHelperAPI {

    TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled);
    void open(@Nullable ScreenAPI screen);
    void playVanillaClickSound();
}