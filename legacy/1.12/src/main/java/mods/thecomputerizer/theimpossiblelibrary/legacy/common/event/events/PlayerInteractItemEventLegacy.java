package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractItemEventWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ITEM;

public class PlayerInteractItemEventLegacy extends PlayerInteractItemEventWrapper<RightClickItem> {

    @SubscribeEvent
    public static void onEvent(RightClickItem event) {
        PLAYER_INTERACT_ITEM.invoke(event);
    }
}