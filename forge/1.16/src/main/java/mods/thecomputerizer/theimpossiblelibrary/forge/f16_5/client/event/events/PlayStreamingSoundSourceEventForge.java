package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayStreamingSoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;

import java.util.Objects;

public class PlayStreamingSoundSourceEventForge extends PlayStreamingSoundSourceEventWrapper<PlayStreamingSourceEvent,ISound> {

    @Override
    protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapNameField() {
        return wrapGenericGetter(PlayStreamingSourceEvent::getName,"");
    }

    @Override
    protected EventFieldWrapper<PlayStreamingSourceEvent,SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override
    protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapUUIDField() { //TODO
        return wrapGenericGetter(event -> "","");
    }
}