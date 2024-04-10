package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseTickEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;

public class LivingItemUseTickEventForge extends LivingItemUseTickEventWrapper<Tick> {

    @Override
    protected EventFieldWrapper<Tick,Integer> wrapDurationField() {
        return wrapGenericBoth(Tick::getDuration, Tick::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Tick,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(Tick::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Tick,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Tick::getItem);
    }
}