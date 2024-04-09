package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockPlaceMultiEventWrapper;
import net.minecraftforge.event.world.BlockEvent.EntityMultiPlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_MULTI;

public class BlockPlaceMultiEventLegacy extends BlockPlaceMultiEventWrapper<EntityMultiPlaceEvent> {

    @SubscribeEvent
    public static void onEvent(EntityMultiPlaceEvent event) {
        BLOCK_PLACE_MULTI.invoke(event);
    }
}