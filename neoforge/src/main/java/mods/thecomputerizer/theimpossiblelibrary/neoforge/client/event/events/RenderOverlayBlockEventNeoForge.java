package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBlockEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;



import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.BLOCK;

public class RenderOverlayBlockEventNeoForge extends RenderOverlayBlockEventWrapper<RenderBlockScreenEffectEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderBlockScreenEffectEvent event) {
        RENDER_OVERLAY_BLOCK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(RenderBlockScreenEffectEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
    
    @Override public void setEvent(RenderBlockScreenEffectEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<RenderBlockScreenEffectEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayBlockType(event.getOverlayType()),BLOCK);
    }
}
