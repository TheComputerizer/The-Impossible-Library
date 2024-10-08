package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY;

public class PlaySoundEvent1_12_2 extends PlaySoundEventWrapper<PlaySoundEvent> {

    @SubscribeEvent
    public static void onEvent(PlaySoundEvent event) {
        SOUND_PLAY.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlaySoundEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<PlaySoundEvent,String> wrapNameField() {
        return wrapGenericGetter(PlaySoundEvent::getName,"");
    }

    @Override protected EventFieldWrapper<PlaySoundEvent,SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getSound()),null);
    }

    @Override protected EventFieldWrapper<PlaySoundEvent,SoundAPI<?>> wrapSoundResultField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getResultSound()),null);
    }
}