package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeXPEventWrapper;
import net.minecraftforge.event.entity.player.PlayerXpEvent.XpChange;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_CHANGE;

public class PlayerChangeXPEventForge extends PlayerChangeXPEventWrapper<XpChange> {
    
    @SubscribeEvent
    public static void onEvent(XpChange event) {
        PLAYER_XP_CHANGE.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(XpChange event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<XpChange,Integer> wrapAmountField() {
        return wrapGenericBoth(XpChange::getAmount,XpChange::setAmount,0);
    }

    @Override
    protected EventFieldWrapper<XpChange,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(XpChange::getPlayer);
    }
}