package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;

public class PlayerPickupItemEventForge extends PlayerPickupItemEventWrapper<ItemPickupEvent> {

    @Override
    protected EventFieldWrapper<ItemPickupEvent,EntityAPI<?>> wrapEntityField() {
        return wrapEntityGetter(ItemPickupEvent::getOriginalEntity);
    }

    @Override
    protected EventFieldWrapper<ItemPickupEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(ItemPickupEvent::getPlayer);
    }

    @Override
    protected EventFieldWrapper<ItemPickupEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(ItemPickupEvent::getStack);
    }
}