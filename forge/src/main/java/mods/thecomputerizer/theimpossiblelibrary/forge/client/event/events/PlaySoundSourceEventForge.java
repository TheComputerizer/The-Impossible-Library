package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_SOURCE;

public class PlaySoundSourceEventForge extends PlaySoundSourceEventWrapper<PlaySoundSourceEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlaySoundSourceEvent event) {
        SOUND_PLAY_SOURCE.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlaySoundSourceEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapNameField() {
        return wrapGenericGetter(PlaySoundSourceEvent::getName,"");
    }

    @Override protected EventFieldWrapper<PlaySoundSourceEvent,SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getSound()),null);
    }

    @Override protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapUUIDField() { //TODO
        return wrapGenericGetter(event -> "","");
    }
}