package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSaveToFileEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SAVE_TO_FILE;

public class PlayerSaveToFileEventLegacy extends PlayerSaveToFileEventWrapper<SaveToFile> {

    @SubscribeEvent
    public static void onEvent(SaveToFile event) {
        PLAYER_SAVE_TO_FILE.invoke(event);
    }
}