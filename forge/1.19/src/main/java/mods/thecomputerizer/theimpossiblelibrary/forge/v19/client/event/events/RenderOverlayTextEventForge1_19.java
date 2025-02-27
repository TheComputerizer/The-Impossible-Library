package mods.thecomputerizer.theimpossiblelibrary.forge.v19.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static net.minecraftforge.client.gui.overlay.VanillaGuiOverlay.TITLE_TEXT;

public class RenderOverlayTextEventForge1_19 extends RenderOverlayTextEventWrapper<RenderGuiOverlayEvent> {
    
    @SubscribeEvent
    public static void onEvent(RenderGuiOverlayEvent event) {
        if(TITLE_TEXT.type().equals(event.getOverlay())) RENDER_OVERLAY_CHAT.invoke(event);
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
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(TITLE_TEXT),ALL);
    }

    //TODO
    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,List<String>> wrapLeftField() {
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }
    
    //TODO
    @Override protected EventFieldWrapper<RenderGuiOverlayEvent,List<String>> wrapRightField() {
        return wrapGenericGetter(event -> new ArrayList<>(),new ArrayList<>());
    }
}