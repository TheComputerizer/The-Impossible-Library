package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.RenderOverlayBlockEventForge;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.BLOCK;

public class RenderOverlayBlockEventForge1_16_5 extends RenderOverlayBlockEventForge<RenderBlockOverlayEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderBlockOverlayEvent event) {
        RENDER_OVERLAY_BLOCK.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderBlockOverlayEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getMatrixStack()));
    }
    
    @Override protected EventFieldWrapper<RenderBlockOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayBlockType(event.getOverlayType()),BLOCK);
    }
}
