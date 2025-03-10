package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.LivingItemUseFinishEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_ITEM_USE_FINISH;

public class LivingItemUseFinishEventNeoForge extends LivingItemUseFinishEventWrapper<Finish> {
    
    @SubscribeEvent
    public static void onEvent(Finish event) {
        LIVING_ITEM_USE_FINISH.invoke(event);
    }
    
    @Override public void setEvent(Finish event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<Finish,Integer> wrapDurationField() {
        return wrapGenericBoth(Finish::getDuration,Finish::setDuration,0);
    }

    @Override protected EventFieldWrapper<Finish,LivingEntityAPI<?,?>> wrapLivingField() {
        return wrapLivingGetter(Finish::getEntity);
    }

    @Override protected EventFieldWrapper<Finish,ItemStackAPI<?>> wrapStackResultField() {
        return wrapItemStackBoth(Finish::getResultStack,Finish::setResultStack);
    }

    @Override protected EventFieldWrapper<Finish,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(Finish::getItem);
    }
}