package mods.thecomputerizer.theimpossiblelibrary.forge.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ForgeHandlesClient implements SharedHandlesClient {
    
    protected boolean loading = true;
    
    @Override public boolean isLoading(@Nullable Object minecraft) {
        return this.loading;
    }
    
    @Override public void onFinishedLoading() {
        this.loading = false;
    }
    
    @Override public void renderDebugText(Object matrix, List<String> text, boolean left) {} //Handled by Forge
    
    @Override public void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height,
            int maxWidth) {}
}