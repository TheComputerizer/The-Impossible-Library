package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceEventWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;

import java.util.function.Function;

public class BlockPlaceEventForge extends BlockPlaceEventWrapper<EntityPlaceEvent> {

    @Override
    protected Function<EntityPlaceEvent,Entity> getEntityFunc() {
        return EntityPlaceEvent::getEntity;
    }

    @Override
    protected Function<EntityPlaceEvent,BlockState> getPlacedFunc() {
        return EntityPlaceEvent::getPlacedBlock;
    }

    @Override
    protected Function<EntityPlaceEvent,BlockState> getPlacedAgainstFunc() {
        return EntityPlaceEvent::getPlacedAgainst;
    }

    @Override
    protected Function<EntityPlaceEvent,IWorld> getWorldFunc() {
        return EntityPlaceEvent::getWorld;
    }
}
