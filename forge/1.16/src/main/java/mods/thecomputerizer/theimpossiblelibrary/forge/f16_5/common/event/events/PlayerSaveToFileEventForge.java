package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSaveToFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.SaveToFile;

import java.io.File;

public class PlayerSaveToFileEventForge extends PlayerSaveToFileEventWrapper<SaveToFile> {

    @Override
    protected EventFieldWrapper<SaveToFile,File> wrapDirectoryField() {
        return wrapGenericGetter(SaveToFile::getPlayerDirectory,null);
    }

    @Override
    protected EventFieldWrapper<SaveToFile,String> wrapUUIDField() {
        return wrapGenericGetter(SaveToFile::getPlayerUUID,null);
    }

    @Override
    protected EventFieldWrapper<SaveToFile,PlayerAPI<?>> wrapPlayerField() {
        return wrapPlayerGetter(SaveToFile::getPlayer);
    }
}