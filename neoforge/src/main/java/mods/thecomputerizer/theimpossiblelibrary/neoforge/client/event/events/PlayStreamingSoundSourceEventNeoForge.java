package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayStreamingSoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.sound.PlayStreamingSourceEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_STREAMING;

public class PlayStreamingSoundSourceEventNeoForge extends PlayStreamingSoundSourceEventWrapper<PlayStreamingSourceEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlayStreamingSourceEvent event) {
        SOUND_PLAY_STREAMING.invoke(event);
    }
    
    @Override public void setEvent(PlayStreamingSourceEvent event) {
        super.setEvent(event);
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapNameField() {
        return wrapGenericGetter(PlayStreamingSourceEvent::getName,"");
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getSound()), null);
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapUUIDField() { //TODO
        return wrapGenericGetter(event -> "","");
    }
}