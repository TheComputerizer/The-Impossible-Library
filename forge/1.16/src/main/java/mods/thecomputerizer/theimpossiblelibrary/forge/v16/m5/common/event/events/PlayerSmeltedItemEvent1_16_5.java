package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSmeltedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;

public class PlayerSmeltedItemEvent1_16_5 extends PlayerSmeltedItemEventWrapper<ItemSmeltedEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemSmeltedEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<ItemSmeltedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemSmeltedEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemSmeltedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemSmeltedEvent::getSmelting);
    }
}