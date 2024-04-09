package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerCloneEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_CLONE;

public class PlayerCloneEventLegacy extends PlayerCloneEventWrapper<Clone> {

    @SubscribeEvent
    public static void onEvent(Clone event) {
        PLAYER_CLONE.invoke(event);
    }
}