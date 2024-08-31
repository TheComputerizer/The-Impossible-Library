package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceMultiEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BlockPlaceMultiEventFabric extends BlockPlaceMultiEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityMultiPlaceEvent::getEntity);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapPlacedAgainstField() {
        return wrapStateGetter(EntityMultiPlaceEvent::getPlacedAgainst);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapPlacedField() {
        return wrapStateGetter(EntityMultiPlaceEvent::getPlacedBlock);
    }

    @Override protected EventFieldWrapper<Object[],BlockSnapshotAPI<?>> wrapSnapshotField() {
        return wrapSnapshotGetter(EntityMultiPlaceEvent::getBlockSnapshot);
    }

    @Override protected EventFieldWrapper<Object[],List<BlockSnapshotAPI<?>>> wrapSnapshotsField() {
        return wrapGenericGetter(event -> event.getReplacedBlockSnapshots().stream()
                .map(WrapperHelper::wrapSnapshot)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityMultiPlaceEvent::getPos);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(EntityMultiPlaceEvent::getState);
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(EntityMultiPlaceEvent::getWorld);
    }
}