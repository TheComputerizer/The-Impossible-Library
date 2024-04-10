package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSmeltedItemEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_SMELTED;

public class PlayerSmeltedItemEventLegacy extends PlayerSmeltedItemEventWrapper<ItemSmeltedEvent> {

    @SubscribeEvent
    public static void onEvent(ItemSmeltedEvent event) {
        PLAYER_ITEM_SMELTED.invoke(event);
    }

    @Override
    protected EventFieldWrapper<ItemSmeltedEvent,ItemStackAPI<?>> wrapStackField() {
        return wrapItemStackGetter(event -> event.smelting);
    }

    @Override
    protected EventFieldWrapper<ItemSmeltedEvent,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(event -> event.player);
    }
}