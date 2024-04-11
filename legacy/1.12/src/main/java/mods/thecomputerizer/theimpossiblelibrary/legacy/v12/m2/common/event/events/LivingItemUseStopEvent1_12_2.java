package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStopEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_STOP;

public class LivingItemUseStopEvent1_12_2 extends LivingItemUseStopEventWrapper<Stop> {

    @SubscribeEvent
    public static void onEvent(Stop event) {
        LIVING_ITEM_USE_STOP.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Stop,Integer> wrapDurationField() {
        return wrapGenericBoth(Stop::getDuration, Stop::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Stop,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(Stop::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Stop,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Stop::getItem);
    }
}