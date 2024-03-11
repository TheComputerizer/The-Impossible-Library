package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

import javax.annotation.Nullable;

public interface MinecraftAPI {

    FontAPI getFont();
    TextAPI<?> getLiteralText(String text);
    RenderAPI getRenderer();
    TextAPI<?> getTranslatedText(String key, Object ... args);
    MinecraftWindow getWindow();
    boolean isCurrentScreen(ScreenAPI<?> screen);
    void setScreen(@Nullable ScreenAPI<?> screen);
}