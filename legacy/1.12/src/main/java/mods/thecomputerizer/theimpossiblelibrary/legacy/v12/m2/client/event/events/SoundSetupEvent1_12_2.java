package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.SoundSetupEventWrapper;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_SETUP;

public class SoundSetupEvent1_12_2 extends SoundSetupEventWrapper<SoundSetupEvent> {

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