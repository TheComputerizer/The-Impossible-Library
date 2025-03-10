package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.RenderOverlayBlockEventForge;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.BLOCK;

public class RenderOverlayBlockEventForge1_19 extends RenderOverlayBlockEventForge<RenderBlockScreenEffectEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderBlockScreenEffectEvent event) {
        RENDER_OVERLAY_BLOCK.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderBlockScreenEffectEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
    
    @Override protected EventFieldWrapper<RenderBlockScreenEffectEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayBlockType(event.getOverlayType()),BLOCK);
    }
}
