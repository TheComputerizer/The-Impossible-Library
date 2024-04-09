package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.LoadSoundEventWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_LOAD;

public class LoadSoundEventLegacy extends LoadSoundEventWrapper<SoundLoadEvent,ISound> {

    @SubscribeEvent
    public static void onEvent(SoundLoadEvent event) {
        SOUND_LOAD.invoke(event);
    }
}