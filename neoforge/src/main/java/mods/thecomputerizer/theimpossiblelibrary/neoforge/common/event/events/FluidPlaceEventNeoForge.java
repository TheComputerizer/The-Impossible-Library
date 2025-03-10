package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.FluidPlaceBlockEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_PLACE_FLUID;

public class FluidPlaceEventNeoForge extends FluidPlaceEventWrapper<FluidPlaceBlockEvent> {
    
    @SubscribeEvent
    public static void onEvent(FluidPlaceBlockEvent event) {
        BLOCK_PLACE_FLUID.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(FluidPlaceBlockEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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