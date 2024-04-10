package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseFinishEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_FINISH;

public class LivingItemUseFinishEventLegacy extends LivingItemUseFinishEventWrapper<Finish> {

    @SubscribeEvent
    public static void onEvent(Finish event) {
        LIVING_ITEM_USE_FINISH.invoke(event);
    }

    @Override
    protected EventFieldWrapper<Finish,Integer> wrapDuration() {
        return wrapGenericBoth(Finish::getDuration,Finish::setDuration,0);
    }

    @Override
    protected EventFieldWrapper<Finish,LivingEntityAPI<?>> wrapLivingField() {
        return wrapLivingGetter(Finish::getEntityLiving);
    }

    @Override
    protected EventFieldWrapper<Finish,ItemStackAPI<?>> wrapResult() {
        return wrapItemStackBoth(Finish::getResultStack,Finish::setResultStack);
    }

    @Override
    protected EventFieldWrapper<Finish,ItemStackAPI<?>> wrapStack() {
        return wrapItemStackGetter(Finish::getItem);
    }
}