package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_SOURCE;

public class PlaySoundSourceEventLegacy extends PlaySoundSourceEventWrapper<PlaySoundSourceEvent,ISound> {

    @SubscribeEvent
    public static void onEvent(PlaySoundSourceEvent event) {
        SOUND_PLAY_SOURCE.invoke(event);
    }

    @Override
    protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapNameField() {
        return getFieldWrapperGetter(PlaySoundSourceEvent::getName,"");
    }

    @Override
    protected EventFieldWrapper<PlaySoundSourceEvent,SoundAPI<ISound>> wrapSoundField() {
        return getFieldWrapperGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override
    protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapUUIDField() {
        return getFieldWrapperGetter(PlaySoundSourceEvent::getUuid,"");
    }
}