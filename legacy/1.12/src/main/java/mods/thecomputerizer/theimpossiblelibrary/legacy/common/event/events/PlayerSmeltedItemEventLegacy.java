package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSmeltedItemEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_SMELTED;

public class PlayerSmeltedItemEventLegacy extends PlayerSmeltedItemEventWrapper<ItemSmeltedEvent> {

    @SubscribeEvent
    public static void onEvent(ItemSmeltedEvent event) {
        PLAYER_ITEM_SMELTED.invoke(event);
    }
}