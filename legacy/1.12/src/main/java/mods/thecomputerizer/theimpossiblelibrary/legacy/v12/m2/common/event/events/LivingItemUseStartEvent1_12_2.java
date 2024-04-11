package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_START;

public class LivingItemUseStartEvent1_12_2 extends LivingItemUseStartEventWrapper<Start> {

    @SubscribeEvent
    public static void onEvent(Start event) {
        LIVING_ITEM_USE_START.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Start,Integer> wrapDurationField() {
        return wrapGenericBoth(Start::getDuration, Start::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Start,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(Start::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Start,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Start::getItem);
    }
}