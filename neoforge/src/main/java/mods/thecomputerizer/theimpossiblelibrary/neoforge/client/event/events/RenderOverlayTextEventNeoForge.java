package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText;


import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.DEBUG;
import static net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay.DEBUG_SCREEN;

public class RenderOverlayTextEventNeoForge extends RenderOverlayTextEventWrapper<DebugText> {
    
    @SubscribeEvent
    public static void onEvent(DebugText event) {
        RENDER_OVERLAY_TEXT.invoke(event);
    }
    
    @Override protected RenderContext initRenderer(DebugText event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getGuiGraphics()));
    }
    
    @Override public void setEvent(DebugText event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<DebugText,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(DEBUG_SCREEN),DEBUG);
    }

    @Override protected EventFieldWrapper<DebugText,List<String>> wrapLeftField() {
        return wrapGenericGetter(DebugText::getLeft,new ArrayList<>());
    }
    
    @Override protected EventFieldWrapper<DebugText,List<String>> wrapRightField() {
        return wrapGenericGetter(DebugText::getRight,new ArrayList<>());
    }
}