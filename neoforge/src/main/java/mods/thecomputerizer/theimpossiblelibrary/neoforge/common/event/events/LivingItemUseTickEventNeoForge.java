package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Tick;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_TICK;

public class LivingItemUseTickEventNeoForge extends LivingItemUseTickEventWrapper<Tick> {
    
    @SubscribeEvent
    public static void onEvent(Tick event) {
        LIVING_ITEM_USE_TICK.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Tick event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<Tick,Integer> wrapDurationField() {
        return wrapGenericBoth(Tick::getDuration, Tick::setDuration,0);
    }

    @Override protected EventFieldWrapper<Tick,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Tick::getEntity);
    }

    @Override protected EventFieldWrapper<Tick,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Tick::getItem);
    }
}