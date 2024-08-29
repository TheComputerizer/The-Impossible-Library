package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;

public class RenderWorldLastEvent1_12_2 extends RenderWorldLastEventWrapper<RenderWorldLastEvent> {

    @SubscribeEvent
    public static void onEvent(RenderWorldLastEvent event) {
        RENDER_WORLD_LAST.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderWorldLastEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks(event.getPartialTicks()));
    }
    
    @Override public void setEvent(RenderWorldLastEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}