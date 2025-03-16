package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.PlaySoundEventForge;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class PlaySoundEventForge1_21 extends PlaySoundEventForge {
    
    @Override protected EventFieldWrapper<PlaySoundEvent,SoundAPI<?>> wrapSoundField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getOriginalSound()), null);
    }
    
    @Override protected EventFieldWrapper<PlaySoundEvent,SoundAPI<?>> wrapSoundResultField() {
        return wrapGenericGetter(event -> WrapperHelper.wrapSoundInstance(event.getSound()),null);
    }
}
