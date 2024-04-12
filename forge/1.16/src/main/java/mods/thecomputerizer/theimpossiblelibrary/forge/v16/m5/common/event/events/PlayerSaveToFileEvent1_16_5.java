package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSaveToFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile;

import java.io.File;

public class PlayerSaveToFileEvent1_16_5 extends PlayerSaveToFileEventWrapper<SaveToFile> {

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
        return wrapPlayerGetter(SaveToFile::getPlayer);
    }
}