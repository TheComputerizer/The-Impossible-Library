package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Client-sided mod loader stuff that is otherwise annoying to abstract
 */
public abstract class SharedHandlesClient {
    
    protected boolean loading;
    
    protected SharedHandlesClient() {
        this(true);
    }
    
    protected SharedHandlesClient(boolean loading) {
        this.loading = loading;
    }
    
    public void endRenderTypeBatch(Object source) {
        endRenderTypeBatch(source,null);
    }
    
    public abstract void endRenderTypeBatch(Object source, @Nullable Object type);
    
    public boolean isLoading(@Nullable Object minecraft) {
        return this.loading;
    }
    
    public void onFinishedLoading() {
        this.loading = false;
    }
    
    public abstract void registerKeyBinding(KeyAPI<?> key);
    
    public void registerKeyBindingsEvent(Object event) {}
    
    @IndirectCallers public void renderDebugText(Object matrix, List<String> left, List<String> right) {
        renderDebugText(matrix,left,true);
        renderDebugText(matrix,right,false);
    }
    
    public abstract void renderDebugText(Object matrix, List<String> text, boolean left);
    public abstract void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height, int maxWidth);
}