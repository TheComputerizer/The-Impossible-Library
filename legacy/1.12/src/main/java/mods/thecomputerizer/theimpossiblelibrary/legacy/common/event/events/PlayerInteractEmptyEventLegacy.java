package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerInteractEmptyEventWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_EMPTY;

public class PlayerInteractEmptyEventLegacy extends PlayerInteractEmptyEventWrapper<RightClickEmpty> {

    @SubscribeEvent
    public static void onEvent(RightClickEmpty event) {
        PLAYER_INTERACT_EMPTY.invoke(event);
    }
}