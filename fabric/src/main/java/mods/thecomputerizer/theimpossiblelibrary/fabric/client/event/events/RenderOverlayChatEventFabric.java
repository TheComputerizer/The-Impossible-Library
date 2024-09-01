package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayChatEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.jetbrains.annotations.NotNull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayChatEventFabric extends RenderOverlayChatEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @SuppressWarnings("NullableProblems") 
    @Override protected RenderContext initRenderer(@NotNull Object[] event) {
        return EventHelper.initRenderer(ctx -> {});
    }

    @Override protected EventFieldWrapper<Object[],OverlayType> wrapOverlayType() {
        return wrapGenericGetter(wrapArrayGetter(0),ALL);
    }

    @Override protected EventFieldWrapper<Object[],Integer> wrapPosXField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,x) -> {},0);
    }

    @Override protected EventFieldWrapper<Object[],Integer> wrapPosYField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,y) -> {},0);
    }
}