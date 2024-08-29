package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidCreateSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_CREATE_FLUID;

public class FluidCreateSourceEventForge extends FluidCreateSourceEventWrapper<CreateFluidSourceEvent> {
    
    @SubscribeEvent
    public static void onEvent(CreateFluidSourceEvent event) {
        BLOCK_CREATE_FLUID.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(CreateFluidSourceEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<CreateFluidSourceEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(CreateFluidSourceEvent::getPos);
    }

    @Override protected EventFieldWrapper<CreateFluidSourceEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(CreateFluidSourceEvent::getState);
    }

    @Override protected EventFieldWrapper<CreateFluidSourceEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(CreateFluidSourceEvent::getWorld);
    }
}