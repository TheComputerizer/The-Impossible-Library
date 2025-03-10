package mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.SoundSetupEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.sound.SoundEngineLoadEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_SETUP;

public class SoundSetupEventNeoForge extends SoundSetupEventWrapper<SoundEngineLoadEvent> {
    
    @SubscribeEvent
    public static void onEvent(SoundEngineLoadEvent event) {
        SOUND_SETUP.invoke(event);
    }
    
    @Override public void setEvent(SoundEngineLoadEvent event) {
        super.setEvent(event);
    }
}