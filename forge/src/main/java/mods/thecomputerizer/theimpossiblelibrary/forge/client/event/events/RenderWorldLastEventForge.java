package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;

public class RenderWorldLastEventForge extends RenderWorldLastEventWrapper<RenderWorldLastEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderWorldLastEvent event) {
        RENDER_WORLD_LAST.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderWorldLastEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getMatrixStack()));
    }
    
    @Override public void setEvent(RenderWorldLastEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}