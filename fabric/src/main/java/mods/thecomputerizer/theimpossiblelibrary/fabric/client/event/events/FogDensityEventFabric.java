package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.FogDensityEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;

import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.minecraft.world.level.timers.TimerQueue.Event;
import org.jetbrains.annotations.NotNull;

public class FogDensityEventFabric extends FogDensityEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @SuppressWarnings("NullableProblems") 
    @Override protected RenderContext initRenderer(@NotNull Object[] event) {
        return EventHelper.initRenderer(ctx -> {});
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapDensityField() {
        return wrapGenericGetter(FogDensity::getDensity,0f);
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(event -> event.getInfo().getEntity());
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}