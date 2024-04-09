package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerBreakSpeedEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_BREAK_SPEED;

public class PlayerBreakSpeedEventLegacy extends PlayerBreakSpeedEventWrapper<BreakSpeed> {

    @SubscribeEvent
    public static void onEvent(BreakSpeed event) {
        PLAYER_BREAK_SPEED.invoke(event);
    }
}