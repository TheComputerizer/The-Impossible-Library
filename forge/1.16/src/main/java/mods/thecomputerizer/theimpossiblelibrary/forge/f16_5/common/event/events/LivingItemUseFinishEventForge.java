package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseFinishEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;

public class LivingItemUseFinishEventForge extends LivingItemUseFinishEventWrapper<Finish> {

    @Override
    protected EventFieldWrapper<Finish,Integer> wrapDurationField() {
        return wrapGenericBoth(Finish::getDuration,Finish::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Finish,LivingEntityAPI<?>> wrapLivingField() {
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