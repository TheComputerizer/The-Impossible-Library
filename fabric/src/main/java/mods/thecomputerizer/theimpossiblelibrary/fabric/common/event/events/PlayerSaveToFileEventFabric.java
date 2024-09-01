package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PlayerSaveToFileEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.io.File;

public class PlayerSaveToFileEventFabric extends PlayerSaveToFileEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],File> wrapDirectoryField() {
        return wrapGenericGetter(wrapArrayGetter(0),null);
    }

    @Override protected EventFieldWrapper<Object[],String> wrapUUIDField() {
        return wrapGenericGetter(wrapArrayGetter(0),null);
    }

    @Override protected EventFieldWrapper<Object[],PlayerAPI<?,?>> wrapPlayerField() {
        return wrapPlayerGetter(wrapArrayGetter(0));
    }
}