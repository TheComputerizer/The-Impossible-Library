package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCraftedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;

public class PlayerCraftedItemEventForge extends PlayerCraftedItemEventWrapper<ItemCraftedEvent> {

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemCraftedEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemCraftedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemCraftedEvent::getCrafting);
    }
}