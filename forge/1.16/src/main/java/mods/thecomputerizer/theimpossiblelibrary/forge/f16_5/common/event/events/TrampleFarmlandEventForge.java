package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.TrampleFarmlandEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;

public class TrampleFarmlandEventForge extends TrampleFarmlandEventWrapper<FarmlandTrampleEvent> {

    @Override
    protected EventFieldWrapper<FarmlandTrampleEvent,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(FarmlandTrampleEvent::getEntity);
    }

    @Override
    protected EventFieldWrapper<FarmlandTrampleEvent,Float> wrapFallDistanceField() {
        return wrapGenericGetter(FarmlandTrampleEvent::getFallDistance,0f);
    }

    @Override
    protected EventFieldWrapper<FarmlandTrampleEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(FarmlandTrampleEvent::getPos);
    }

    @Override
    protected EventFieldWrapper<FarmlandTrampleEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FarmlandTrampleEvent::getState);
    }

    @Override
    protected EventFieldWrapper<FarmlandTrampleEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(FarmlandTrampleEvent::getWorld);
    }
}