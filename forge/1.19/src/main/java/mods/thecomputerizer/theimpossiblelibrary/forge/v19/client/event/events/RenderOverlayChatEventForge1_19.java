package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayChatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.CHAT_PANEL;

public class RenderOverlayChatEventForge1_19 extends RenderOverlayChatEventWrapper<RenderGuiOverlayEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderGuiOverlayEvent event) {
        if(CHAT_PANEL.type().equals(event.getOverlay())) RENDER_OVERLAY_CHAT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(@Nonnull RenderGuiOverlayEvent event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getPoseStack()));
    }
    
    @Override public void setEvent(RenderGuiOverlayEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(CHAT_PANEL),ALL);
    }
    
    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,Integer> wrapPosXField() {
        return wrapGenericGetter(event -> 0,0);
    }
    
    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,Integer> wrapPosYField() {
        return wrapGenericGetter(event -> 0,0);
    }
}