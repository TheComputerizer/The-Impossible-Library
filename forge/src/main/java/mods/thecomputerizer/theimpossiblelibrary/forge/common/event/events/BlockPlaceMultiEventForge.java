package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceMultiEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.EntityMultiPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_MULTI;

public class BlockPlaceMultiEventForge extends BlockPlaceMultiEventWrapper<EntityMultiPlaceEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityMultiPlaceEvent event) {
        BLOCK_PLACE_MULTI.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityMultiPlaceEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityMultiPlaceEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,BlockStateAPI<?>> wrapPlacedAgainstField() {
        return wrapStateGetter(EntityMultiPlaceEvent::getPlacedAgainst);
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,BlockStateAPI<?>> wrapPlacedField() {
        return wrapStateGetter(EntityMultiPlaceEvent::getPlacedBlock);
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,BlockSnapshotAPI<?>> wrapSnapshotField() {
        return wrapSnapshotGetter(EntityMultiPlaceEvent::getBlockSnapshot);
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,List<BlockSnapshotAPI<?>>> wrapSnapshotsField() {
        return wrapGenericGetter(event -> event.getReplacedBlockSnapshots().stream()
                .map(WrapperHelper::wrapSnapshot)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()),new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityMultiPlaceEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(EntityMultiPlaceEvent::getState);
    }

    @Override
    protected EventFieldWrapper<EntityMultiPlaceEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(EntityMultiPlaceEvent::getWorld);
    }
}