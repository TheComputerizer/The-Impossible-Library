package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;

public interface MinecraftAPI {

    FontAPI getFont();
    TextAPI<?> getLiteralText(String text);
    RenderAPI getRenderer();
    TextAPI<?> getTranslatedText(String key, Object ... args);
    MinecraftWindow getWindow();
    boolean isCurrentScreen(ScreenAPI<?> screen);
}