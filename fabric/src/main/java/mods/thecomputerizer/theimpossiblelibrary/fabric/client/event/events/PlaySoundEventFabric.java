package mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.ClientFabricEvent;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.resources.sounds.SoundInstance;

import java.util.Objects;

public class PlaySoundEventFabric extends PlaySoundEventWrapper<Object[],SoundInstance> implements ClientFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }

    @Override
    protected EventFieldWrapper<Object[],String> wrapNameField() {
        return wrapGenericGetter(PlaySoundEvent::getName,"");
    }

    @Override
    protected EventFieldWrapper<Object[],SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override
    protected EventFieldWrapper<Object[],SoundAPI<ISound>> wrapSoundResultField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getResultSound()) : null,null);
    }
}