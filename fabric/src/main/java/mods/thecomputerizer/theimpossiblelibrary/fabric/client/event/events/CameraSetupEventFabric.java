package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CameraSetupEventFabric extends CameraSetupEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @SuppressWarnings("NullableProblems") 
    @Override protected RenderContext initRenderer(@NotNull Object[] args) {
        return EventHelper.initRenderer(ctx -> ((Number)args[0]).floatValue());
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapPitchField() {
        return wrapGenericGetter(args -> ((Number)args[1]).floatValue(),0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapRollField() {
        return wrapGenericGetter(args -> ((Number)args[2]).floatValue(),0f);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapYawField() {
        return wrapGenericGetter(args -> ((Number)args[3]).floatValue(),0f);
    }

    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(args -> (Entity)args[4]);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(args -> (BlockState)args[5]);
    }
}