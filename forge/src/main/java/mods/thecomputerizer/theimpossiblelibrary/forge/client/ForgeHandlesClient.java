package mods.thecomputerizer.theimpossiblelibrary.forge.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import java.util.List;

/**
 * Handled by Forge in all versions except 1.16.5?
 */
public abstract class ForgeHandlesClient extends SharedHandlesClient {
    
    /**
     * Handled by Forge in all versions
     */
    @Override public void renderDebugText(Object o, List<String> l, boolean b) {}
    
    /**
     * Handled by Forge in all versions except 1.16.5?
     */
    @Override public void renderToolTip(RenderAPI r, List<?> l, int x, int y, int w, int h, int maxW) {}
}