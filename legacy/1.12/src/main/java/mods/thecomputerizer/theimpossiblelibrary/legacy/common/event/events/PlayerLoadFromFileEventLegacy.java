package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoadFromFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOAD_FROM_FILE;

public class PlayerLoadFromFileEventLegacy extends PlayerLoadFromFileEventWrapper<LoadFromFile> {

    @SubscribeEvent
    public static void onEvent(LoadFromFile event) {
        PLAYER_LOAD_FROM_FILE.invoke(event);
    }

    @Override
    protected EventFieldWrapper<LoadFromFile,File> wrapDirectoryField() {
        return wrapGenericGetter(LoadFromFile::getPlayerDirectory,null);
    }

    @Override
    protected EventFieldWrapper<LoadFromFile,String> wrapUUIDField() {
        return wrapGenericGetter(LoadFromFile::getPlayerUUID,null);
    }

    @Override
    protected EventFieldWrapper<LoadFromFile,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(LoadFromFile::getEntityPlayer);
    }
}