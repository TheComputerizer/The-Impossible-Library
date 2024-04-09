package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStopEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_STOP;

public class LivingItemUseStopEventLegacy extends LivingItemUseStopEventWrapper<Stop> {

    @SubscribeEvent
    public static void onEvent(Stop event) {
        LIVING_ITEM_USE_STOP.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Stop,Integer> wrapDuration() {
        return wrapGenericBoth(Stop::getDuration, Stop::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Stop,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(Stop::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Stop,ItemStackAPI<?>> wrapStack() {
        return wrapItemStackGetter(Stop::getItem);
    }
}