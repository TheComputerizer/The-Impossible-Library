package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.TEXT;
import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.RENDER_DEBUG_INFO;

public class RenderOverlayTextEventFabric extends RenderOverlayTextEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return RENDER_DEBUG_INFO;
    }
    
    @SuppressWarnings("NullableProblems") 
    @Override protected RenderContext initRenderer(@NotNull Object[] event) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(event[0]));
    }

    @Override protected EventFieldWrapper<Object[],OverlayType> wrapOverlayType() {
        return wrapGenericGetter(e -> TEXT,TEXT);
    }

    @Override protected EventFieldWrapper<Object[],List<String>> wrapLeftField() {
        return wrapGenericGetter(wrapArrayGetter(1),new ArrayList<>());
    }

    @Override protected EventFieldWrapper<Object[],List<String>> wrapRightField() {
        return wrapGenericGetter(wrapArrayGetter(2),new ArrayList<>());
    }
}