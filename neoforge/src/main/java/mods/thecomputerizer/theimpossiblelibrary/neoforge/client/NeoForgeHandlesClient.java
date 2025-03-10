package mods.thecomputerizer.theimpossiblelibrary.neoforge.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import java.util.List;

/**
 * Only keybinds & RenderType stuff needs to be handled in 1.20.4+
 */
public abstract class NeoForgeHandlesClient extends SharedHandlesClient {
    
    @Override public void renderDebugText(Object o, List<String> l, boolean b) {}
    
    @Override public void renderToolTip(RenderAPI r, List<?> l, int x, int y, int w, int h, int maxW) {}
}