package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCraftedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_CRAFTED;

public class PlayerCraftedItemEventLegacy extends PlayerCraftedItemEventWrapper<ItemCraftedEvent> {

    @SubscribeEvent
    public static void onEvent(ItemCraftedEvent event) {
        PLAYER_ITEM_CRAFTED.invoke(event);
    }

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(event -> event.crafting);
    }
}