package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_START;

public class LivingItemUseStartEventForge extends LivingItemUseStartEventWrapper<Start> {
    
    @SubscribeEvent
    public static void onEvent(Start event) {
        LIVING_ITEM_USE_START.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Start event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<Start,Integer> wrapDurationField() {
        return wrapGenericBoth(Start::getDuration, Start::setDuration,0);
    }

    @Override protected EventFieldWrapper<Start,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Start::getEntityLiving);
    }

    @Override protected EventFieldWrapper<Start,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Start::getItem);
    }
}