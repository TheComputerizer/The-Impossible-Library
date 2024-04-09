package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.HarvestBlockDropsEventWrapper;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_HARVEST;

public class HarvestBlockDropsEventLegacy extends HarvestBlockDropsEventWrapper<HarvestDropsEvent> {

    @SubscribeEvent
    public static void onEvent(HarvestDropsEvent event) {
        BLOCK_HARVEST.invoke(event);
    }
}