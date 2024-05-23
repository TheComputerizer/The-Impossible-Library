package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

public class BlockPlaceEvent1_16_5 extends BlockPlaceEventWrapper<EntityPlaceEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(EntityPlaceEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<EntityPlaceEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityPlaceEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<EntityPlaceEvent,BlockStateAPI<?>> wrapPlacedAgainstField() {
        return wrapStateGetter(EntityPlaceEvent::getPlacedAgainst);
    }

    @Override
    protected EventFieldWrapper<EntityPlaceEvent,BlockStateAPI<?>> wrapPlacedField() {
        return wrapStateGetter(EntityPlaceEvent::getPlacedBlock);
    }

    @Override
    protected EventFieldWrapper<EntityPlaceEvent,BlockSnapshotAPI<?>> wrapSnapshotField() {
        return wrapSnapshotGetter(EntityPlaceEvent::getBlockSnapshot);
    }

    @Override
    protected EventFieldWrapper<EntityPlaceEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityPlaceEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<EntityPlaceEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(EntityPlaceEvent::getState);
    }

    @Override
    protected EventFieldWrapper<EntityPlaceEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(EntityPlaceEvent::getWorld);
    }
}