package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceEventWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE;

public class BlockPlaceEventLegacy extends BlockPlaceEventWrapper<EntityPlaceEvent> {

    @SubscribeEvent
    public static void onEvent(EntityPlaceEvent event) {
        BLOCK_PLACE.invoke(event);
    }

    @Override
    protected Function<EntityPlaceEvent,Entity> getEntityFunc() {
        return EntityPlaceEvent::getEntity;
    }

    @Override
    protected Function<EntityPlaceEvent,IBlockState> getPlacedFunc() {
        return EntityPlaceEvent::getPlacedBlock;
    }

    @Override
    protected Function<EntityPlaceEvent,IBlockState> getPlacedAgainstFunc() {
        return EntityPlaceEvent::getPlacedAgainst;
    }

    @Override
    protected Function<EntityPlaceEvent,World> getWorldFunc() {
        return EntityPlaceEvent::getWorld;
    }
}
