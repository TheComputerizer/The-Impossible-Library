package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCraftedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_CRAFTED;

public class PlayerCraftedItemEventNeoForge extends PlayerCraftedItemEventWrapper<ItemCraftedEvent> {
    
    @SubscribeEvent
    public static void onEvent(ItemCraftedEvent event) {
        PLAYER_ITEM_CRAFTED.invoke(event);
    }
    
    @Override public void setEvent(ItemCraftedEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<ItemCraftedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemCraftedEvent::getEntity);
    }

    @Override protected EventFieldWrapper<ItemCraftedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemCraftedEvent::getCrafting);
    }
}