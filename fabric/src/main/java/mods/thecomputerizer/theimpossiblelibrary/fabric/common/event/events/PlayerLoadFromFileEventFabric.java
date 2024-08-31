package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerLoadFromFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.io.File;

public class PlayerLoadFromFileEventFabric extends PlayerLoadFromFileEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],File> wrapDirectoryField() {
        return wrapGenericGetter(LoadFromFile::getPlayerDirectory,null);
    }

    @Override protected EventFieldWrapper<Object[],String> wrapUUIDField() {
        return wrapGenericGetter(LoadFromFile::getPlayerUUID,null);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(LoadFromFile::getPlayer);
    }
}