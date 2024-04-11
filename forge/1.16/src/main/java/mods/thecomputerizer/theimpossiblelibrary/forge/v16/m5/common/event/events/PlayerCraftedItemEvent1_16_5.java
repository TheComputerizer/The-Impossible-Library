package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCraftedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

public class PlayerCraftedItemEvent1_16_5 extends PlayerCraftedItemEventWrapper<ItemCraftedEvent> {

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemCraftedEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemCraftedEvent::getCrafting);
    }
}