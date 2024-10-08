package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Client-sided mod loader stuff that is otherwise annoying to abstract
 */
public interface SharedHandlesClient {
    
    boolean isLoading(@Nullable Object minecraft);
    void registerKeyBinding(KeyAPI<?> key);
    void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height, int maxWidth);
}