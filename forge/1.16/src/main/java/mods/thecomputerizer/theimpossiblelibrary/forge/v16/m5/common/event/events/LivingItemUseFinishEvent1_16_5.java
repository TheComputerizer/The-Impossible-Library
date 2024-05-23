package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseFinishEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;

public class LivingItemUseFinishEvent1_16_5 extends LivingItemUseFinishEventWrapper<Finish> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Finish event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<Finish,Integer> wrapDurationField() {
        return wrapGenericBoth(Finish::getDuration,Finish::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Finish,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Finish::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Finish,ItemStackAPI<?>> wrapStackResultField() {
        return wrapItemStackBoth(Finish::getResultStack,Finish::setResultStack);
    }

    @Override
    protected EventFieldWrapper<Finish,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Finish::getItem);
    }
}