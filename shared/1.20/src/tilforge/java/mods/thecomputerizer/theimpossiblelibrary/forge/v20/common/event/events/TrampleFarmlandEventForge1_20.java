package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.TrampleFarmlandEventForge;
import net.minecraftforge.event.level.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_TRAMPLE_FARMLAND;

public class TrampleFarmlandEventForge1_20 extends TrampleFarmlandEventForge<FarmlandTrampleEvent> {
    
    @SubscribeEvent
    public static void onEvent(FarmlandTrampleEvent event) {
        BLOCK_TRAMPLE_FARMLAND.invoke(event);
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
        return wrapWorldGetter(FarmlandTrampleEvent::getLevel);
    }
}