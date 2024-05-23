package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundSourceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;

import java.util.Objects;

public class PlaySoundSourceEvent1_16_5 extends PlaySoundSourceEventWrapper<PlaySoundSourceEvent,ISound> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PlaySoundSourceEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

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