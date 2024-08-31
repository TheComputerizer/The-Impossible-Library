package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class BlockPlaceEventFabric extends BlockPlaceEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityPlaceEvent::getEntity);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapPlacedAgainstField() {
        return wrapStateGetter(EntityPlaceEvent::getPlacedAgainst);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapPlacedField() {
        return wrapStateGetter(EntityPlaceEvent::getPlacedBlock);
    }

    @Override protected EventFieldWrapper<Object[],BlockSnapshotAPI<?>> wrapSnapshotField() {
        return wrapSnapshotGetter(EntityPlaceEvent::getBlockSnapshot);
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityPlaceEvent::getPos);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(EntityPlaceEvent::getState);
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(EntityPlaceEvent::getWorld);
    }
}