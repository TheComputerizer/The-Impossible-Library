package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.TrampleFarmlandEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_TRAMPLE_FARMLAND;

public class TrampleFarmlandEventLegacy extends TrampleFarmlandEventWrapper<FarmlandTrampleEvent> {

    @SubscribeEvent
    public static void onEvent(FarmlandTrampleEvent event) {
        BLOCK_TRAMPLE_FARMLAND.invoke(event);
    }

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