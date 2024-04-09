package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerNameFormatEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_NAME_FORMAT;

public class PlayerNameFormatEventLegacy extends PlayerNameFormatEventWrapper<NameFormat> {

    @SubscribeEvent
    public static void onEvent(NameFormat event) {
        PLAYER_NAME_FORMAT.invoke(event);
    }
}