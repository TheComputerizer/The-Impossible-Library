package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;

public class RenderOverlayBlockEvent1_12_2 extends RenderOverlayBlockEventWrapper<RenderBlockOverlayEvent> {

    @SubscribeEvent
    public static void onEvent(RenderBlockOverlayEvent event) {
        RENDER_OVERLAY_BLOCK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderBlockOverlayEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.setPartialTicks(event.getRenderPartialTicks()));
    }
    
    @Override public void setEvent(RenderBlockOverlayEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<RenderBlockOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayBlockType(event.getOverlayType()),OverlayType.BLOCK);
    }
}