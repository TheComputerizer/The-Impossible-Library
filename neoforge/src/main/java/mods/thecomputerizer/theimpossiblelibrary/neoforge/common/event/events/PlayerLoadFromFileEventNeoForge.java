package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoadFromFileEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.LoadFromFile;

import java.io.File;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOAD_FROM_FILE;

public class PlayerLoadFromFileEventNeoForge extends PlayerLoadFromFileEventWrapper<LoadFromFile> {
    
    @SubscribeEvent
    public static void onEvent(LoadFromFile event) {
        PLAYER_LOAD_FROM_FILE.invoke(event);
    }
    
    @Override public void setEvent(LoadFromFile event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<LoadFromFile,File> wrapDirectoryField() {
        return wrapGenericGetter(LoadFromFile::getPlayerDirectory,null);
    }

    @Override protected EventFieldWrapper<LoadFromFile,String> wrapUUIDField() {
        return wrapGenericGetter(LoadFromFile::getPlayerUUID,null);
    }

    @Override protected EventFieldWrapper<LoadFromFile,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LoadFromFile::getEntity);
    }
}