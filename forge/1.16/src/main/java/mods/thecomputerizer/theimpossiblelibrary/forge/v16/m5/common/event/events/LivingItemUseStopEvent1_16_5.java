package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStopEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;

public class LivingItemUseStopEvent1_16_5 extends LivingItemUseStopEventWrapper<Stop> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Stop event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<Stop,Integer> wrapDurationField() {
        return wrapGenericBoth(Stop::getDuration, Stop::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Stop,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Stop::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Stop,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Stop::getItem);
    }
}