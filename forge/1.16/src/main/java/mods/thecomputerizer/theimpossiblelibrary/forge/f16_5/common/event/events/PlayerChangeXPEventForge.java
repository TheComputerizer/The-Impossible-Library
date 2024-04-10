package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerXpEvent.XpChange;

public class PlayerChangeXPEventForge extends PlayerChangeXPEventWrapper<XpChange> {

    @Override
    protected EventFieldWrapper<XpChange,Integer> wrapAmountField() {
        return wrapGenericBoth(XpChange::getAmount,XpChange::setAmount,0);
    }

    @Override
    protected EventFieldWrapper<XpChange,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(XpChange::getPlayer);
    }
}