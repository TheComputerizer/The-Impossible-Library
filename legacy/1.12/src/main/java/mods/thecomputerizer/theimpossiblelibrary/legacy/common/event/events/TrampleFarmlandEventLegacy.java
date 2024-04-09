package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.TrampleFarmlandEventWrapper;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_TRAMPLE_FARMLAND;

public class TrampleFarmlandEventLegacy extends TrampleFarmlandEventWrapper<FarmlandTrampleEvent> {

    @SubscribeEvent
    public static void onEvent(FarmlandTrampleEvent event) {
        BLOCK_TRAMPLE_FARMLAND.invoke(event);
    }
}