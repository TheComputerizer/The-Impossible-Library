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
        return wrapPosGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapNewStateField() {
        return wrapStateBoth(wrapArrayGetter(0),(args,state) -> {});
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapOriginalStateField() {
        return wrapStateGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(wrapArrayGetter(0));
    }

    @Override protected EventFieldWrapper<Object[],WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(wrapArrayGetter(0));
    }
}