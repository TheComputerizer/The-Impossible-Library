package mods.thecomputerizer.theimpossiblelibrary.forge.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import java.util.List;

public abstract class ForgeHandlesClient implements SharedHandlesClient {
    
    @Override public void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height,
            int maxWidth) {}
}