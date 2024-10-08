package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import java.util.List;

//TODO Maybe abstract 1.12.2 stuff here?
public class LegacyHandlesClient implements SharedHandlesClient {
    
    @Override public boolean isLoading(Object minecraft) {
        return false;
    }
    
    @Override public void registerKeyBinding(KeyAPI<?> key) {}
    
    @Override public void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height,
            int maxWidth) {}
}