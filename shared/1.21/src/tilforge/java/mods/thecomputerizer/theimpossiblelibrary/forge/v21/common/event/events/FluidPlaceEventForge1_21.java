package mods.thecomputerizer.theimpossiblelibrary.forge.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.FluidPlaceEventForge;
import net.minecraftforge.event.level.BlockEvent.FluidPlaceBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_FLUID;

public class FluidPlaceEventForge1_21 extends FluidPlaceEventForge<FluidPlaceBlockEvent> {
    
    @SubscribeEvent
    public static void onEvent(FluidPlaceBlockEvent event) {
        BLOCK_PLACE_FLUID.invoke(event);
    }
    
    @Override protected EventFieldWrapper<FluidPlaceBlockEvent,BlockPosAPI<?>> wrapFluidPosField() {
        return wrapPosGetter(FluidPlaceBlockEvent::getLiquidPos);
    }

    @Override protected EventFieldWrapper<FluidPlaceBlockEvent,BlockStateAPI<?>> wrapNewStateField() {
        return wrapStateBoth(FluidPlaceBlockEvent::getNewState,FluidPlaceBlockEvent::setNewState);
    }

    @Override protected EventFieldWrapper<FluidPlaceBlockEvent,BlockStateAPI<?>> wrapOriginalStateField() {
        return wrapStateGetter(FluidPlaceBlockEvent::getOriginalState);
    }

    @Override protected EventFieldWrapper<FluidPlaceBlockEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(FluidPlaceBlockEvent::getPos);
    }

    @Override protected EventFieldWrapper<FluidPlaceBlockEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FluidPlaceBlockEvent::getState);
    }

    @Override protected EventFieldWrapper<FluidPlaceBlockEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(FluidPlaceBlockEvent::getLevel);
    }
}