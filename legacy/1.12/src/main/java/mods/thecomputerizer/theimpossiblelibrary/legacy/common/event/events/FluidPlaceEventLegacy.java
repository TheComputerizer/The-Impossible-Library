package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidPlaceEventWrapper;
import net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_FLUID;

public class FluidPlaceEventLegacy extends FluidPlaceEventWrapper<FluidPlaceBlockEvent> {

    @SubscribeEvent
    public static void onEvent(FluidPlaceBlockEvent event) {
        BLOCK_PLACE_FLUID.invoke(event);
    }
}