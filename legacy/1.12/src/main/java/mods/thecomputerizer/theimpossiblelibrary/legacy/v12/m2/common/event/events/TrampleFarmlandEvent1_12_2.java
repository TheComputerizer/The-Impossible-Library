package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.TrampleFarmlandEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_TRAMPLE_FARMLAND;

public class TrampleFarmlandEvent1_12_2 extends TrampleFarmlandEventWrapper<FarmlandTrampleEvent> {

    @SubscribeEvent
    public static void onEvent(FarmlandTrampleEvent event) {
        BLOCK_TRAMPLE_FARMLAND.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(FarmlandTrampleEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<FarmlandTrampleEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(FarmlandTrampleEvent::getEntity);
    }

    @Override protected EventFieldWrapper<FarmlandTrampleEvent,Float> wrapFallDistanceField() {
        return wrapGenericGetter(FarmlandTrampleEvent::getFallDistance,0f);
    }

    @Override protected EventFieldWrapper<FarmlandTrampleEvent,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(FarmlandTrampleEvent::getPos);
    }

    @Override protected EventFieldWrapper<FarmlandTrampleEvent,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(FarmlandTrampleEvent::getState);
    }

    @Override protected EventFieldWrapper<FarmlandTrampleEvent,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(FarmlandTrampleEvent::getWorld);
    }
}