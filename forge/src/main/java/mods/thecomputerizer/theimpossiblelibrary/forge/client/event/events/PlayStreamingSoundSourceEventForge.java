package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlayStreamingSoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_STREAMING;

public class PlayStreamingSoundSourceEventForge extends PlayStreamingSoundSourceEventWrapper<PlayStreamingSourceEvent,ISound> {
    
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

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override protected EventFieldWrapper<PlayStreamingSourceEvent,String> wrapUUIDField() { //TODO
        return wrapGenericGetter(event -> "","");
    }
}