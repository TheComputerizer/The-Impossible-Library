package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockSnapshotAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.BlockPlaceEventForge;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE;

public class BlockPlaceEventForge1_16_5 extends BlockPlaceEventForge<EntityPlaceEvent> {
    
    @SubscribeEvent
    public static void onEvent(EntityPlaceEvent event) {
        BLOCK_PLACE.invoke(event);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(EntityPlaceEvent::getEntity);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,BlockStateAPI<?>> wrapPlacedAgainstField() {
        return wrapStateGetter(EntityPlaceEvent::getPlacedAgainst);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,BlockStateAPI<?>> wrapPlacedField() {
        return wrapStateGetter(EntityPlaceEvent::getPlacedBlock);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,BlockSnapshotAPI<?>> wrapSnapshotField() {
        return wrapSnapshotGetter(EntityPlaceEvent::getBlockSnapshot);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(EntityPlaceEvent::getPos);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(EntityPlaceEvent::getState);
    }
    
    @Override protected EventFieldWrapper<EntityPlaceEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(EntityPlaceEvent::getWorld);
    }
}
