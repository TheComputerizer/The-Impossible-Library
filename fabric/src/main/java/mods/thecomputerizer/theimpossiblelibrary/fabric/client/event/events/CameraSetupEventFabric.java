package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import org.jetbrains.annotations.NotNull;

public class CameraSetupEventFabric extends CameraSetupEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @SuppressWarnings("NullableProblems") 
    @Override protected RenderContext initRenderer(@NotNull Object[] args) {
        return EventHelper.initRenderer(ctx -> {});
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapPitchField() {
        return wrapGenericGetter(wrapArrayGetter(1),0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapRollField() {
        return wrapGenericGetter(wrapArrayGetter(2),0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapYawField() {
        return wrapGenericGetter(wrapArrayGetter(3),0f);
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(wrapArrayGetter(4));
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(wrapArrayGetter(5));
    }
}