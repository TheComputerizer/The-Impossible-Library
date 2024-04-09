package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoadFromFileEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOAD_FROM_FILE;

public class PlayerLoadFromFileEventLegacy extends PlayerLoadFromFileEventWrapper<LoadFromFile> {

    @SubscribeEvent
    public static void onEvent(LoadFromFile event) {
        PLAYER_LOAD_FROM_FILE.invoke(event);
    }
}