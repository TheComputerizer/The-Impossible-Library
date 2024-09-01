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
        return wrapEntityGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapPlacedAgainstField() {
        return wrapStateGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapPlacedField() {
        return wrapStateGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockSnapshotAPI<?>> wrapSnapshotField() {
        return wrapSnapshotGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(wrapArrayGetter(0));
    }
}