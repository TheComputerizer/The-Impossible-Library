package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidCreateSourceEventWrapper;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_CREATE_FLUID;

public class FluidCreateSourceEventLegacy extends FluidCreateSourceEventWrapper<CreateFluidSourceEvent> {

    @SubscribeEvent
    public static void onEvent(CreateFluidSourceEvent event) {
        BLOCK_CREATE_FLUID.invoke(event);
    }
}