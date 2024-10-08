package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayStreamingSoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_STREAMING;

public class PlayStreamingSoundSourceEvent1_12_2 extends PlayStreamingSoundSourceEventWrapper<PlayStreamingSourceEvent> {

    @SubscribeEvent
    public static void onEvent(PlayStreamingSourceEvent event) {
        SOUND_PLAY_STREAMING.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlayStreamingSourceEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapNameField() {
        return wrapGenericGetter(PlayStreamingSourceEvent::getName,"");
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getSound()), null);
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapUUIDField() {
        return wrapGenericGetter(PlayStreamingSourceEvent::getUuid,"");
    }
}