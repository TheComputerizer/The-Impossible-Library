package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseStopEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Stop;

public class LivingItemUseStopEventForge extends LivingItemUseStopEventWrapper<Stop> {

    @Override
    protected EventFieldWrapper<Stop,Integer> wrapDurationField() {
        return wrapGenericBoth(Stop::getDuration, Stop::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Stop,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(Stop::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Stop,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Stop::getItem);
    }
}