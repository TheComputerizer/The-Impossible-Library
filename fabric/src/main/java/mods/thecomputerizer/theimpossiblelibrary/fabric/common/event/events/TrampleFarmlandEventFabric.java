package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.TrampleFarmlandEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class TrampleFarmlandEventFabric extends TrampleFarmlandEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(FarmlandTrampleEvent::getEntity);
    }

    @Override protected EventFieldWrapper<Object[],Float> wrapFallDistanceField() {
        return wrapGenericGetter(FarmlandTrampleEvent::getFallDistance,0f);
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(FarmlandTrampleEvent::getPos);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FarmlandTrampleEvent::getState);
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(FarmlandTrampleEvent::getWorld);
    }
}