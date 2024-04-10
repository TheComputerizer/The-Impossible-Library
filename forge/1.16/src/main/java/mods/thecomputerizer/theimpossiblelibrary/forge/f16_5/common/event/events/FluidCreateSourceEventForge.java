package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.FluidCreateSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;

public class FluidCreateSourceEventForge extends FluidCreateSourceEventWrapper<CreateFluidSourceEvent> {

    @Override
    protected EventFieldWrapper<CreateFluidSourceEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(CreateFluidSourceEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<CreateFluidSourceEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(CreateFluidSourceEvent::getState);
    }

    @Override
    protected EventFieldWrapper<CreateFluidSourceEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(CreateFluidSourceEvent::getWorld);
    }
}