package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.SoundSetupEventWrapper;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_SETUP;

public class SoundSetupEventForge extends SoundSetupEventWrapper<SoundSetupEvent,ISound> {
    
    @SubscribeEvent
    public static void onEvent(SoundSetupEvent event) {
        SOUND_SETUP.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(SoundSetupEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
}