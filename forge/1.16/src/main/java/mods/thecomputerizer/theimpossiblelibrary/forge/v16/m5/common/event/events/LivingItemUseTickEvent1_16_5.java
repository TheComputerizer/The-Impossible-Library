package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;

public class LivingItemUseTickEvent1_16_5 extends LivingItemUseTickEventWrapper<Tick> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Tick event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<Tick,Integer> wrapDurationField() {
        return wrapGenericBoth(Tick::getDuration, Tick::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Tick,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Tick::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Tick,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Tick::getItem);
    }
}