package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.FluidCreateSourceEventForge;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_CREATE_FLUID;

public class FluidCreateSourceEventForge1_16_5 extends FluidCreateSourceEventForge<CreateFluidSourceEvent> {
    
    @SubscribeEvent
    public static void onEvent(CreateFluidSourceEvent event) {
        BLOCK_CREATE_FLUID.invoke(event);
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