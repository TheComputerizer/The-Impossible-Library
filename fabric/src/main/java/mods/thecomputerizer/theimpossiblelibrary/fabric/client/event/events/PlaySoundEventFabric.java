package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;

public class PlaySoundEventFabric extends PlaySoundEventWrapper<Object[]> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }

    @Override protected EventFieldWrapper<Object[],String> wrapNameField() {
        return wrapGenericGetter(wrapArrayGetter(0),"");
    }

    @Override protected EventFieldWrapper<Object[],SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(wrapArrayGetter(0),null);
    }

    @Override protected EventFieldWrapper<Object[],SoundAPI<?>> wrapSoundResultField() {
        return wrapGenericGetter(wrapArrayGetter(0),null);
    }
}