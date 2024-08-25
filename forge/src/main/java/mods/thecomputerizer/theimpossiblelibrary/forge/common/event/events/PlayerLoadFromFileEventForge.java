package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoadFromFileEventWrapper;
import net.minecraftforge.event.entity.player.PlayerEvent.LoadFromFile;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.File;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_LOAD_FROM_FILE;

public class PlayerLoadFromFileEventForge extends PlayerLoadFromFileEventWrapper<LoadFromFile> {
    
    @SubscribeEvent
    public static void onEvent(LoadFromFile event) {
        PLAYER_LOAD_FROM_FILE.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(LoadFromFile event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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
    protected EventFieldWrapper<LoadFromFile,PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LoadFromFile::getPlayer);
    }
}