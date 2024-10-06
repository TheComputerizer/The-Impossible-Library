package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.event.Event;

import javax.annotation.Nonnull;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.START;

public class RenderOverlayPreEventFabric extends RenderOverlayPreEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return START;
    }
    
    @Override protected RenderContext initRenderer(@Nonnull Object[] event) {
        return EventHelper.initRenderer(ctx -> {
            WorldRenderContext worldRender = (WorldRenderContext)event[0];
            ctx.getRenderer().setMatrix(worldRender.matrixStack());
            ctx.setPartialTicks(worldRender.tickDelta());
        });
    }

    @Override protected EventFieldWrapper<Object[],OverlayType> wrapOverlayType() {
        return wrapGenericGetter(ev -> ALL,ALL);
    }
}