package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStartEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Start;

public class LivingItemUseStartEventForge extends LivingItemUseStartEventWrapper<Start> {

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