package mods.thecomputerizer.theimpossiblelibrary.api.client;

import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Client-sided mod loader stuff that is otherwise annoying to abstract
 */
public interface SharedHandlesClient {
    
    default void endRenderTypeBatch(Object source) {
        endRenderTypeBatch(source,null);
    }
    
    void endRenderTypeBatch(Object source, @Nullable Object type);
    boolean isLoading(@Nullable Object minecraft);
    void onFinishedLoading();
    void registerKeyBinding(KeyAPI<?> key);
    
    @IndirectCallers default void renderDebugText(Object matrix, List<String> left, List<String> right) {
        renderDebugText(matrix,left,true);
        renderDebugText(matrix,right,false);
    }
    
    void renderDebugText(Object matrix, List<String> text, boolean left);
    void renderToolTip(RenderAPI renderer, List<?> lines, int x, int y, int width, int height, int maxWidth);
}