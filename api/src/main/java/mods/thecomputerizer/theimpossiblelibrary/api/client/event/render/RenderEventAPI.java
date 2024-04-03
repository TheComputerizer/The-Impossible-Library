package mods.thecomputerizer.theimpossiblelibrary.api.client.event.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

public interface RenderEventAPI<M> extends ClientEventAPI<M> {

    float getPartialTicks();
    RenderAPI getRenderContext();
}