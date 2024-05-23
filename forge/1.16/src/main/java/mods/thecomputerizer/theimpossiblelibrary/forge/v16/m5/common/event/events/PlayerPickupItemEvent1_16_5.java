package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;

public class PlayerPickupItemEvent1_16_5 extends PlayerPickupItemEventWrapper<ItemPickupEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(ItemPickupEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    protected EventFieldWrapper<ItemPickupEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(ItemPickupEvent::getOriginalEntity);
    }

    @Override
    protected EventFieldWrapper<ItemPickupEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemPickupEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemPickupEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemPickupEvent::getStack);
    }
}