package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeXPEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent.XpChange;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_CHANGE;

public class PlayerChangeXPEventNeoForge extends PlayerChangeXPEventWrapper<XpChange> {
    
    @SubscribeEvent
    public static void onEvent(XpChange event) {
        PLAYER_XP_CHANGE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(XpChange event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<XpChange,Integer> wrapAmountField() {
        return wrapGenericBoth(XpChange::getAmount,XpChange::setAmount,0);
    }

    @Override protected EventFieldWrapper<XpChange,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(XpChange::getEntity);
    }
}