package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayStreamingSoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.resources.sounds.SoundInstance;

import java.util.Objects;

public class PlayStreamingSoundSourceEventFabric extends PlayStreamingSoundSourceEventWrapper<Object[],SoundInstance> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object[],String> wrapNameField() {
        return wrapGenericGetter(PlayStreamingSourceEvent::getName,"");
    }

    @Override
    protected EventFieldWrapper<Object[],SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override
    protected EventFieldWrapper<Object[],String> wrapUUIDField() { //TODO
        return wrapGenericGetter(event -> "","");
    }
}