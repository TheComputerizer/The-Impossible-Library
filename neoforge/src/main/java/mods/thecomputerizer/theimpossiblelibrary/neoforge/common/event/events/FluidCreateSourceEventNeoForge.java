package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidCreateSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.CreateFluidSourceEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_CREATE_FLUID;

public class FluidCreateSourceEventNeoForge extends FluidCreateSourceEventWrapper<CreateFluidSourceEvent> {
    
    @SubscribeEvent
    public static void onEvent(CreateFluidSourceEvent event) {
        BLOCK_CREATE_FLUID.invoke(event);
    }
    
    @Override public void setEvent(CreateFluidSourceEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<CreateFluidSourceEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(CreateFluidSourceEvent::getPos);
    }

    @Override protected EventFieldWrapper<CreateFluidSourceEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(CreateFluidSourceEvent::getState);
    }

    @Override protected EventFieldWrapper<CreateFluidSourceEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(CreateFluidSourceEvent::getLevel);
    }
}