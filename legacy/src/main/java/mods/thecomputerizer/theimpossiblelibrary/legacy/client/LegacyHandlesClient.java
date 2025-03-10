package mods.thecomputerizer.theimpossiblelibrary.legacy.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import java.util.List;

/**
 * Forge is the only supported mod loader in 1.12.2, so these are handled in its MinecraftAPI implementation
 */
public class LegacyHandlesClient extends SharedHandlesClient {
    
    @Override public void endRenderTypeBatch(Object s, Object t) {}
    
    @Override public void registerKeyBinding(KeyAPI<?> k) {}
    
    @Override public void renderDebugText(Object o, List<String> l, boolean b) {}
    
    @Override public void renderToolTip(RenderAPI r, List<?> l, int x, int y, int w, int m, int maxW) {}
}