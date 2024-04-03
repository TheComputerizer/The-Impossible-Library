package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import javax.annotation.Nullable;

public interface MinecraftAPI {

    FontAPI getFont();
    RenderAPI getRenderer();
    MinecraftWindow getWindow();
    boolean isCurrentScreen(ScreenAPI<?> screen);
    void setScreen(@Nullable ScreenAPI<?> screen);
}