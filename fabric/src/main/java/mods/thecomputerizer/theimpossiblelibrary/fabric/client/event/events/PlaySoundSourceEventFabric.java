package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.resources.sounds.SoundInstance;

public class PlaySoundSourceEventFabric extends PlaySoundSourceEventWrapper<Object[],SoundInstance> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object[],String> wrapNameField() {
        return wrapGenericGetter(wrapArrayGetter(0),"");
    }

    @Override
    protected EventFieldWrapper<Object[],SoundAPI<SoundInstance>> wrapSoundField() {
        return wrapGenericGetter(wrapArrayGetter(0),null);
    }

    @Override
    protected EventFieldWrapper<Object[],String> wrapUUIDField() { //TODO
        return wrapGenericGetter(wrapArrayGetter(0),"");
    }
}