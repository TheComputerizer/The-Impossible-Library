package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY;

public class PlaySoundEvent1_12_2 extends PlaySoundEventWrapper<PlaySoundEvent,ISound> {

    @SubscribeEvent
    public static void onEvent(PlaySoundEvent event) {
        SOUND_PLAY.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlaySoundEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected EventFieldWrapper<PlaySoundEvent,String> wrapNameField() {
        return wrapGenericGetter(PlaySoundEvent::getName,"");
    }

    @Override
    protected EventFieldWrapper<PlaySoundEvent,SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override
    protected EventFieldWrapper<PlaySoundEvent,SoundAPI<ISound>> wrapSoundResultField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getResultSound()) : null,null);
    }
}