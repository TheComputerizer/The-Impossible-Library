package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.PlaySoundEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY;

public class PlaySoundEventNeoForge extends PlaySoundEventWrapper<PlaySoundEvent> {
    
    @SubscribeEvent
    public static void onEvent(PlaySoundEvent event) {
        SOUND_PLAY.invoke(event);
    }
    
    @Override public void setEvent(PlaySoundEvent event) {
        super.setEvent(event);
    }
    
    @Override protected EventFieldWrapper<PlaySoundEvent,String> wrapNameField() {
        return wrapGenericGetter(PlaySoundEvent::getName,"");
    }
    
    @Override protected EventFieldWrapper<PlaySoundEvent,SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getOriginalSound()), null);
    }
    
    @Override protected EventFieldWrapper<PlaySoundEvent,SoundAPI<?>> wrapSoundResultField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getSound()),null);
    }
}
