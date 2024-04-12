package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSaveToFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SAVE_TO_FILE;

public class PlayerSaveToFileEvent1_12_2 extends PlayerSaveToFileEventWrapper<SaveToFile> {

    @SubscribeEvent
    public static void onEvent(SaveToFile event) {
        PLAYER_SAVE_TO_FILE.invoke(event);
    }

    @Override
    protected EventFieldWrapper<SaveToFile,File> wrapDirectoryField() {
        return wrapGenericGetter(SaveToFile::getPlayerDirectory,null);
    }

    @Override
    protected EventFieldWrapper<SaveToFile,String> wrapUUIDField() {
        return wrapGenericGetter(SaveToFile::getPlayerUUID,null);
    }

    @Override
    protected EventFieldWrapper<SaveToFile,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(SaveToFile::getEntityPlayer);
    }
}