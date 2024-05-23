package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.XpChange;

public class PlayerChangeXPEvent1_16_5 extends PlayerChangeXPEventWrapper<XpChange> {
    
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