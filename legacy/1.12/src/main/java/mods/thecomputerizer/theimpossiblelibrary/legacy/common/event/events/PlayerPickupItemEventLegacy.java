package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerPickupItemEventWrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_PICKUP;

public class PlayerPickupItemEventLegacy extends PlayerPickupItemEventWrapper<ItemPickupEvent> {

    @SubscribeEvent
    public static void onEvent(ItemPickupEvent event) {
        PLAYER_ITEM_PICKUP.invoke(event);
    }
}