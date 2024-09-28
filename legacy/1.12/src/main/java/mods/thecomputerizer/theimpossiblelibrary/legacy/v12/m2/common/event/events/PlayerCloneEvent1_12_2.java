package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCloneEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CLONE;

public class PlayerCloneEvent1_12_2 extends PlayerCloneEventWrapper<Clone> {

    @SubscribeEvent
    public static void onEvent(Clone event) {
        PLAYER_CLONE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Clone event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Clone,Boolean> wrapDeathField() {
        return wrapGenericGetter(Clone::isWasDeath,true);
    }

    @Override protected EventFieldWrapper<Clone,PlayerAPI<?,?>> wrapOriginalField() {
        return wrapPlayerGetter(Clone::getOriginal);
    }

    @Override protected EventFieldWrapper<Clone,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(Clone::getEntityPlayer);
    }
}