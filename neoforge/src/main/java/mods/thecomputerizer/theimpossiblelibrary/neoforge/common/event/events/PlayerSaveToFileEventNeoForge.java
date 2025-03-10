package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSaveToFileEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.SaveToFile;

import java.io.File;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_SAVE_TO_FILE;

public class PlayerSaveToFileEventNeoForge extends PlayerSaveToFileEventWrapper<SaveToFile> {
    
    @SubscribeEvent
    public static void onEvent(SaveToFile event) {
        PLAYER_SAVE_TO_FILE.invoke(event);
    }
    
    @Override public void setEvent(SaveToFile event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<SaveToFile,File> wrapDirectoryField() {
        return wrapGenericGetter(SaveToFile::getPlayerDirectory,null);
    }

    @Override protected EventFieldWrapper<SaveToFile,String> wrapUUIDField() {
        return wrapGenericGetter(SaveToFile::getPlayerUUID,null);
    }

    @Override protected EventFieldWrapper<SaveToFile,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(SaveToFile::getEntity);
    }
}