package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerVisibilityEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.Visibility;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_VISIBILITY;

public class PlayerVisibilityEventLegacy extends PlayerVisibilityEventWrapper<Visibility> {

    @SubscribeEvent
    public static void onEvent(Visibility event) {
        PLAYER_VISIBILITY.invoke(event);
    }
}