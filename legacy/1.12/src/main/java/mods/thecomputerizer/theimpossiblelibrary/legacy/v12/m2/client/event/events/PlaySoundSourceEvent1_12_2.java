package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_SOURCE;

public class PlaySoundSourceEvent1_12_2 extends PlaySoundSourceEventWrapper<PlaySoundSourceEvent,ISound> {

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

    @Override protected EventFieldWrapper<PlaySoundSourceEvent,SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapUUIDField() {
        return wrapGenericGetter(PlaySoundSourceEvent::getUuid,"");
    }
}