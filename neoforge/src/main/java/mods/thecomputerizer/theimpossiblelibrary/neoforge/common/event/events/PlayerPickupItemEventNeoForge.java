package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemPickupEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_PICKUP;

public class PlayerPickupItemEventNeoForge extends PlayerPickupItemEventWrapper<ItemPickupEvent> {
    
    @SubscribeEvent
    public static void onEvent(ItemPickupEvent event) {
        PLAYER_ITEM_PICKUP.invoke(event);
    }
    
    @Override public void setEvent(ItemPickupEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<ItemPickupEvent,EntityAPI<?,?>> wrapEntityField() {
        return wrapEntityGetter(ItemPickupEvent::getOriginalEntity);
    }

    @Override protected EventFieldWrapper<ItemPickupEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemPickupEvent::getEntity);
    }

    @Override protected EventFieldWrapper<ItemPickupEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemPickupEvent::getStack);
    }
}