package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;

import java.util.Objects;

public class PlaySoundSourceEventForge extends PlaySoundSourceEventWrapper<PlaySoundSourceEvent,ISound> {

    @Override
    protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapNameField() {
        return wrapGenericGetter(PlaySoundSourceEvent::getName,"");
    }

    @Override
    protected EventFieldWrapper<PlaySoundSourceEvent,SoundAPI<ISound>> wrapSoundField() {
        return wrapGenericGetter(event -> Objects.nonNull(this.soundHelper) ? this.soundHelper.getAPI(event.getSound()) : null,null);
    }

    @Override
    protected EventFieldWrapper<PlaySoundSourceEvent,String> wrapUUIDField() { //TODO
        return wrapGenericGetter(event -> "","");
    }
}