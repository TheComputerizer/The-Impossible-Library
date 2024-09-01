package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderWorldLastEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.event.Event;
import org.jetbrains.annotations.NotNull;

import static net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.LAST;

public class RenderWorldLastEventFabric extends RenderWorldLastEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return LAST;
    }
    
    @SuppressWarnings("NullableProblems") 
    @Override protected RenderContext initRenderer(@NotNull Object[] args) {
        return EventHelper.initRenderer(ctx -> ctx.getRenderer().setMatrix(((WorldRenderContext)args[0]).matrixStack()));
    }
}