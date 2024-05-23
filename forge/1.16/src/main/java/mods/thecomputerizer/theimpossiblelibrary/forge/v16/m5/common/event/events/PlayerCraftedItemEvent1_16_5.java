package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCraftedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

public class PlayerCraftedItemEvent1_16_5 extends PlayerCraftedItemEventWrapper<ItemCraftedEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemCraftedEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<ItemCraftedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemCraftedEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemCraftedEvent::getCrafting);
    }
}