package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class FluidPlaceEventFabric extends FluidPlaceEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapFluidPosField() {
        return wrapPosGetter(FluidPlaceBlockEvent::getLiquidPos);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapNewStateField() {
        return wrapStateBoth(FluidPlaceBlockEvent::getNewState,FluidPlaceBlockEvent::setNewState);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapOriginalStateField() {
        return wrapStateGetter(FluidPlaceBlockEvent::getOriginalState);
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(FluidPlaceBlockEvent::getPos);
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FluidPlaceBlockEvent::getState);
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(FluidPlaceBlockEvent::getWorld);
    }
}