package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStopEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_STOP;

public class LivingItemUseStopEventForge extends LivingItemUseStopEventWrapper<Stop> {
    
    @SubscribeEvent
    public static void onEvent(Stop event) {
        LIVING_ITEM_USE_STOP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Stop event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<Stop,Integer> wrapDurationField() {
        return wrapGenericBoth(Stop::getDuration, Stop::setDuration,0);
    }

    @Override protected EventFieldWrapper<Stop,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Stop::getEntityLiving);
    }

    @Override protected EventFieldWrapper<Stop,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Stop::getItem);
    }
}