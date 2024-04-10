package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.FluidPlaceBlockEvent;

public class FluidPlaceEventForge extends FluidPlaceEventWrapper<FluidPlaceBlockEvent> {

    @Override
    protected EventFieldWrapper<FluidPlaceBlockEvent,BlockPosAPI<?>> wrapFluidPosField() {
        return wrapPosGetter(FluidPlaceBlockEvent::getLiquidPos);
    }

    @Override
    protected EventFieldWrapper<FluidPlaceBlockEvent,BlockStateAPI<?>> wrapNewStateField() {
        return wrapStateBoth(FluidPlaceBlockEvent::getNewState,FluidPlaceBlockEvent::setNewState);
    }

    @Override
    protected EventFieldWrapper<FluidPlaceBlockEvent,BlockStateAPI<?>> wrapOriginalStateField() {
        return wrapStateGetter(FluidPlaceBlockEvent::getOriginalState);
    }

    @Override
    protected EventFieldWrapper<FluidPlaceBlockEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(FluidPlaceBlockEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<FluidPlaceBlockEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FluidPlaceBlockEvent::getState);
    }

    @Override
    protected EventFieldWrapper<FluidPlaceBlockEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(FluidPlaceBlockEvent::getWorld);
    }
}