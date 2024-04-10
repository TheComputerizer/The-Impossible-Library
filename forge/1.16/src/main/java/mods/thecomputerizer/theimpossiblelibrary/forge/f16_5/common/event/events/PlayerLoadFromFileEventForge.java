package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoadFromFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile;

import java.io.File;

public class PlayerLoadFromFileEventForge extends PlayerLoadFromFileEventWrapper<LoadFromFile> {

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
        return wrapPlayerGetter(LoadFromFile::getPlayer);
    }
}