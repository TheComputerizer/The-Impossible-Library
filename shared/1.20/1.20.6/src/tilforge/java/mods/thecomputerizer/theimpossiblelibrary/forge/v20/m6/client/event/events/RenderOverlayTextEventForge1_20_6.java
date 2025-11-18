package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.DEBUG;
import static net.minecraftforge.client.event.CustomizeGuiOverlayEvent.DebugText.Side.Left;
import static net.minecraftforge.client.event.CustomizeGuiOverlayEvent.DebugText.Side.Right;

public class RenderOverlayTextEventForge1_20_6 extends RenderOverlayTextEventWrapper<DebugText> {
    
    @SubscribeEvent
    public static void onEvent(DebugText event) {
        RENDER_OVERLAY_TEXT.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override protected RenderContext initRenderer(DebugText event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event.getGuiGraphics()));
    }
    
    @Override public void setEvent(DebugText event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<DebugText,OverlayType> wrapOverlayType() {
        return wrapGenericGetter(event -> EventHelper.getOverlayElementType(DEBUG),ALL);
    }

    @Override protected EventFieldWrapper<DebugText,List<String>> wrapLeftField() {
        return wrapGenericGetter(event -> event.getSide()==Left ? event.getText() : new ArrayList<>(),
                                 new ArrayList<>());
    }
    
    @Override protected EventFieldWrapper<DebugText,List<String>> wrapRightField() {
        return wrapGenericGetter(event -> event.getSide()==Right ? event.getText() : new ArrayList<>(),
                                 new ArrayList<>());
    }
}