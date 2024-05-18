package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
public class GeometryHelper {

    public static final List<AnchoredGeometry> ANCHORED_RENDERS = Collections.synchronizedList(new ArrayList<>());
    public static final List<ITickableGeometry<?>> TICKABLE_RENDERS = Collections.synchronizedList(new ArrayList<>());

    public static void render(RenderContext ctx, float partialTicks) {
        synchronized(ANCHORED_RENDERS) {
            Iterator<AnchoredGeometry> renderItr = ANCHORED_RENDERS.iterator();
            while(renderItr.hasNext()) {
                AnchoredGeometry staticRender = renderItr.next();
                if(staticRender.isEmpty()) {
                    renderItr.remove();
                    if(staticRender instanceof ITickableGeometry<?>) TICKABLE_RENDERS.remove(staticRender);
                }
                else staticRender.render(ctx,partialTicks);
            }
        }
    }
    
    public static void tick() {
        Iterator<ITickableGeometry<?>> tickItr = TICKABLE_RENDERS.iterator();
        while(tickItr.hasNext()) {
            ITickableGeometry<?> tickable = tickItr.next();
            tickable.onTick();
            if(!tickable.isInitialized()) {
                tickItr.remove();
                if(tickable instanceof AnchoredGeometry) TICKABLE_RENDERS.remove(tickable);
            }
        }
    }
}