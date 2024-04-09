package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockNotifyNeighborEventWrapper;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_NOTIFY_NEIGHBOR;

public class BlockNotifyNeighborEventLegacy extends BlockNotifyNeighborEventWrapper<NeighborNotifyEvent> {

    @SubscribeEvent
    public static void onEvent(NeighborNotifyEvent event) {
        BLOCK_NOTIFY_NEIGHBOR.invoke(event);
    }
}