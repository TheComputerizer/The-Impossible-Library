package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSmeltedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemSmeltedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_SMELTED;

public class PlayerSmeltedItemEventNeoForge extends PlayerSmeltedItemEventWrapper<ItemSmeltedEvent> {
    
    @SubscribeEvent
    public static void onEvent(ItemSmeltedEvent event) {
        PLAYER_ITEM_SMELTED.invoke(event);
    }
    
    @Override public void setEvent(ItemSmeltedEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<ItemSmeltedEvent,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemSmeltedEvent::getEntity);
    }

    @Override protected EventFieldWrapper<ItemSmeltedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemSmeltedEvent::getSmelting);
    }
}