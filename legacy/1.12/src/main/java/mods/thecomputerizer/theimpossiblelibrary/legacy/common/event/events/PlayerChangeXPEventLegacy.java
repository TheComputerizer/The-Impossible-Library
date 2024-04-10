package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerChangeXPEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;

public class PlayerChangeXPEventLegacy extends PlayerChangeXPEventWrapper<PlayerPickupXpEvent> { //TODO This is wrong

    @Override
    protected EventFieldWrapper<PlayerPickupXpEvent,Integer> wrapAmountField() {
        return wrapGenericBoth(event -> 0,(event,amount) -> {},0);
    }

    @Override
    protected EventFieldWrapper<PlayerPickupXpEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> null);
    }
    
}