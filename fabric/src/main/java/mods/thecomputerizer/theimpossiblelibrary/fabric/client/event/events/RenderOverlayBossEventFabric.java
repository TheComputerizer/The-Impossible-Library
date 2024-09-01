package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayBossEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.jetbrains.annotations.NotNull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;

public class RenderOverlayBossEventFabric extends RenderOverlayBossEventWrapper<Object[]> implements ClientFabricEvent {
    
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

    @Override protected EventFieldWrapper<Object[],Integer> wrapIncrementField() {
        return wrapGenericBoth(wrapArrayGetter(0),(args,increment) -> {},0);
    }

    @Override
    protected EventFieldWrapper<Object[],Integer> wrapXField() {
        return wrapGenericGetter(wrapArrayGetter(0),0);
    }

    @Override
    protected EventFieldWrapper<Object[],Integer> wrapYField() {
        return wrapGenericGetter(wrapArrayGetter(0),0);
    }
}